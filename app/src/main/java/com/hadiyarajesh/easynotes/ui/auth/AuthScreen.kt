package com.hadiyarajesh.easynotes.ui.auth

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.ui.components.SignInButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthScreen(navController: NavController,
               viewModel: AuthViewModel = hiltViewModel()
) {
    val status by viewModel.loadingState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){result->
        Log.e("TAG", "Google Sign In Failed ${dumpIntent(result.data!!)}" )
        if(result.resultCode == RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try{
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                Log.e("TAG", "$account, $credential" )
                viewModel.signInWithGoogle(credential)
            }
            catch (e: Exception){
                Log.e("TAG", "Google Sign In Failed" )
            }
        }
    }
    AuthView(isLoading) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context,gso)
        launcher.launch(googleSignInClient.signInIntent)
    }
    when(status.status){
        LoadingState.Status.SUCCESS->{
            isLoading = true
            Log.e("TAG", "Google Sign In Failed" )
        }
        LoadingState.Status.FAILED->{
            isLoading = false
            Log.e("TAG", "Google Sign In Failed" )
        }
        else->{
            isLoading = false
            Log.e("TAG", "Google Sign In Failed" )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthView(isLoading: Boolean, onClick: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SignInButton(text = "Sign in with Google",
                loadingText = "Signing in...",
                isLoading = isLoading,
                icon = painterResource(id = R.drawable.google),
                onClick = {
                    onClick()
                })
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

fun dumpIntent(i: Intent) {
    val bundle: Bundle? = i.getExtras()
    if (bundle != null) {
        val keys: Set<String> = bundle.keySet()
        val it = keys.iterator()
        Log.e("TAG", "Dumping Intent start")
        while (it.hasNext()) {
            val key = it.next()
            Log.e("LOG_TAG", "[" + key + "=" + bundle.get(key) + "]")
        }
        Log.e("LOG_TAG", "Dumping Intent end")
    }
}