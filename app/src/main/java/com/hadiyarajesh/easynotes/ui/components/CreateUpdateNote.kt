package com.hadiyarajesh.easynotes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.ui.createnote.CreateNoteViewModel


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

    Scaffold(
    topBar = { AddNoteTopBar(
        onBackClick = { navController.popBackStack() },
        title = stringResource(id = R.string.create_note),
        createNoteClick = {},
        insertMediaClick = {}
    ) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
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
                    .fillMaxWidth(),
                onValueChange = {
                    noteTitle = it
                },
                placeholder = { Text("Add Title") },
            )
            dropDownUI()
            Row(
                modifier = if(darkTheme)
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = Color.Black)
                else
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownUI() {
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
                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
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
