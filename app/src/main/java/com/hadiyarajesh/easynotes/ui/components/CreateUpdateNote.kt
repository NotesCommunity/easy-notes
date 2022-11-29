package com.hadiyarajesh.easynotes.ui.components

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.ui.createnote.CreateNoteViewModel
import com.hadiyarajesh.easynotes.utility.GetMediaActivityResultContract
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateNote(
    isUpdate: Boolean,
    darkTheme: Boolean,
    navController: NavController,
    createNoteViewModel: CreateNoteViewModel
) {
    var noteTitle by remember { mutableStateOf("") }
    var noteDescription by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
    topBar = { AddNoteTopBar(
        onBackClick = { navController.popBackStack() },
        title = stringResource(id = R.string.create_note),
        createNoteClick = {},
    ) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            MediaContainer(context, darkTheme)
            TextField(
                value = noteTitle,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
//                    backgroundColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onValueChange = {
                    noteTitle = it
                },
                placeholder = { Text("Add Title") },
            )
            dropDownUI()
            Row(
                modifier = if (darkTheme)
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = Color.Black)
                else
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
            ) {
                Divider(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                        .width(6.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = Color.Cyan,
                )
                TextField(
                    value = noteDescription,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        containerColor = if(darkTheme) Color.Black else  Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = Color.White),
                    onValueChange = {
                        noteDescription = it
                    },
                    shape = RoundedCornerShape(0.dp),
                    placeholder = { Text("Add Description") },
                )
            }

        }
    }


}

private fun saveImageInInternalStorage(imageUri: MutableList<Uri>, context: Context, filename: String, bitmap: Bitmap) : Boolean{
    return try{
        context.openFileOutput("$filename.jpg", MODE_PRIVATE).use { os ->
            if(!bitmap.compress(Bitmap.CompressFormat.JPEG, 40, os)){
                throw IOException("Couldn't save photo")
            }
        }
        imageUri.add(Uri.parse("${context.filesDir}/$filename.jpg"))
        true
    }catch (e: IOException){
        e.printStackTrace()
        false
    }
}


@Composable
private fun MediaContainer(context: Context, isDark: Boolean){

    val imageUri = remember { mutableStateListOf<Uri>()}

    val open = remember {
        mutableStateOf(false)
    }

    val cameraLauncher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.TakePicturePreview()) {
        if(it!=null){
            saveImageInInternalStorage(imageUri, context, UUID.randomUUID().toString(), it!!)
        }

    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = GetMediaActivityResultContract()
    ) { list: List<Uri>? ->
        //use the received Uri
        if(list!=null && list.isNotEmpty()){
            imageUri.addAll(list)
        }
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetMultipleContents()){ list: List<Uri>? ->
        if(list!=null && list.isNotEmpty()){
            imageUri.addAll(list)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                1.dp,
                if (isDark) Color.White else Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .height(50.dp)
                .border(
                    1.dp,
                    if (isDark) Color.White else Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            IconButton(onClick = {
                open.value = true
//                cameraLauncher.launch("*/*")
//                launcher.launch("*/*")
//                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
        if(open.value) customDialog(openDialogCustom = open, launcher, galleryLauncher, cameraLauncher)
        ShowMediaList(context, imageUri, isDark)
    }
}

@Composable
private fun customDialog(
    openDialogCustom: MutableState<Boolean>,
    launcher: ManagedActivityResultLauncher<String, List<@JvmSuppressWildcards Uri>>,
    galleryLauncher: ManagedActivityResultLauncher<String, List<@JvmSuppressWildcards Uri>>,
    cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>
){
    Dialog(onDismissRequest = {
        openDialogCustom.value = false
    }) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            Text("Insert Media", style = TextStyle(
                fontSize = 16.sp
            ))

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
//                        .fillMaxSize()
                        .clickable {
                            openDialogCustom.value = false
                            launcher.launch("*/*")
                        }
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painter = painterResource(id = R.drawable.google_docs), contentDescription = null)
                    Text(modifier = Modifier.padding(top = 8.dp), text = "Document", style = TextStyle(
                        fontSize = 12.sp
                    ))
                }
                Column(
                    modifier = Modifier
//                        .fillMaxSize()
                        .clickable {
                            openDialogCustom.value = false
                            cameraLauncher.launch()
                        }
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painter = painterResource(id = R.drawable.photo_camera), contentDescription = null)
                    Text(modifier = Modifier.padding(top = 8.dp), text = "Camera", style = TextStyle(
                        fontSize = 12.sp
                    ))
                }
                Column(
                    modifier = Modifier
//                        .fillMaxSize()
                        .clickable {
                            openDialogCustom.value = false
                            galleryLauncher.launch("*/*")
                        }
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painter = painterResource(id = R.drawable.image_gallery), contentDescription = null)
                    Text(modifier = Modifier.padding(top = 8.dp), text = "Gallery", style = TextStyle(
                        fontSize = 12.sp
                    ))
                }
            }

        }

    }
}

@Composable
private fun ShowMediaList(context: Context, list: MutableList<Uri>, isDark: Boolean){

    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val offsetInPx = LocalDensity.current.run { (24 / 2) }
    LazyRow(
//        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        items(
            count = list.size,
            itemContent = { it1->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                        .border(
                            1.dp,
                            if (isDark) Color.White else Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(list[it1].toString())
                                )
                            )
                        }
                ) {
                    if(list[it1].path!!.lowercase().contains("image") ||
                        list[it1].path!!.lowercase().contains("jpg") ||
                        list[it1].path!!.lowercase().contains("picture") ||
                        list[it1].path!!.lowercase().contains("shot")){
                        ShowImage(imageUri = list[it1], bitmap, context)
                    }else if(list[it1].path!!.lowercase().contains("video") ||
                            list[it1].path!!.lowercase().contains("recorder")){
                        ShowVideo()
                    }else if(list[it1].path!!.lowercase().contains("document")){
                        ShowPDF()
                    }
                    Icon(
                        modifier = Modifier
//                            .offset {
//                            IntOffset(x = +offsetInPx, y = -offsetInPx)
//                        }
                            .clip(CircleShape)
                            .background(White)
                            .size(20.dp)
                            .align(Alignment.TopEnd)
                            .clickable { list.removeAt(it1) },
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel_24),
                        contentDescription = null)
                }
            })
    }
}

@Composable
private fun ShowVideo(){
    Icon(modifier = Modifier
        .padding(8.dp)
        .height(34.dp),
        painter = painterResource(id = R.drawable.play_button),
        contentDescription = null)
}

@Composable
private fun ShowPDF(){
    Icon(modifier = Modifier
        .padding(8.dp)
        .height(34.dp),
        painter = painterResource(id = R.drawable.pdf),
        contentDescription = null, tint = Color.Unspecified)
}

@Composable
private fun ShowImage(imageUri: Uri?, bitmap: MutableState<Bitmap?>, context: Context) {
    imageUri.let {
        if(it?.path!!.contains("jpg")){
            Image(painter = rememberAsyncImagePainter(it),
                contentDescription =null,
                modifier = Modifier.size(50.dp))

        }else{
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val source = it?.let { it1 ->
                    ImageDecoder
                        .createSource(context.contentResolver, it1)
                }
                bitmap.value = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
            }


            bitmap.value?.let {  btm ->
                Image(bitmap = compressBitmap(btm).asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier.size(50.dp))
            }
        }


    }
}

fun compressBitmap(btm: Bitmap): Bitmap{
    val baos = ByteArrayOutputStream()
    btm.compress(Bitmap.CompressFormat.JPEG, 40, baos)
    return btm
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun dropDownUI() {
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a list of cities
    val mCities = listOf("Work Notes", "Readings", "Random", "Private")

    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("Select category") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(top = 10.dp, bottom = 10.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        TextField(
            value = mSelectedText,
            readOnly = true,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(color = Color.Transparent)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .padding(0.dp)
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            mCities.forEach { label ->
                DropdownMenuItem(text = { Text(text = label) }, onClick = {
                    mSelectedText = label
                    mExpanded = false
                })
            }
        }
    }
}
