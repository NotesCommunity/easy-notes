package com.hadiyarajesh.easynotes.ui.auth

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.ui.components.SignInButton
import com.hadiyarajesh.easynotes.ui.components.VerticalSpacer
import com.hadiyarajesh.easynotes.ui.navigation.TopLevelDestination
import com.hadiyarajesh.easynotes.ui.theme.Purple80

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    navController: NavController, viewModel: AuthViewModel
) {

    val status by viewModel.loadingState.collectAsState()
    val userSignedIn by viewModel.userSignedIn.collectAsState(initial = false)
    var isLoading by remember { mutableStateOf(false) }
    var errorText by remember {
        mutableStateOf("")
    }
    if(userSignedIn == true){
        navController.navigate(TopLevelDestination.Notes.route)
    }

    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.e("TAG", "AuthScreen: $account" )
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    viewModel.signInWithGoogle(credential)
                } catch (e: Exception) {
                    Log.e("TAG", "Google Sign In Failed ${e.localizedMessage}")
                }
            }
        }
    AuthView(isLoading) {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("719734510327-fkl6rvtae915bkc6j62u0d987jge5d0u.apps.googleusercontent.com")
                .requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        launcher.launch(googleSignInClient.signInIntent)
    }
    when (status.status) {
        LoadingState.Status.SUCCESS -> {
            isLoading = false
        }
        LoadingState.Status.RUNNING -> {
            isLoading = true
        }
        else -> {
            isLoading = false
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
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            VerticalSpacer(size = 40)
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "",
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp)
            )
            SignInButton(
                text = "Sign in with Google",
                icon = painterResource(id = R.drawable.google),
                isLoading = isLoading
            ) {
                onClick()
            }
            Text(
                text = stringResource(id = R.string.tandc),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                fontSize = 16.sp
            )
        }
    }
}
