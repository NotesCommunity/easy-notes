package com.hadiyarajesh.easynotes.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.ui.components.ErrorText
import com.hadiyarajesh.easynotes.ui.components.SignInButton
import com.hadiyarajesh.easynotes.ui.components.VerticalSpacer
import com.hadiyarajesh.easynotes.utility.AuthResultContract
import com.hadiyarajesh.easynotes.utility.Constants
import com.hadiyarajesh.easynotes.utility.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val singInRequestCode = rememberSaveable { 1 }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var errorText by rememberSaveable { mutableStateOf<String?>(null) }
    val authState by remember { authViewModel.authState }.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is UiState.Success -> {
                isLoading = false
                errorText = null
                // No need to navigate to EasyNotesApp() here as change in authenticated state flow in MainActivity will automatically redirect to EasyNotesApp()
            }

            is UiState.Error -> {
                isLoading = false
                errorText = (authState as UiState.Error).data
            }

            else -> {}
        }
    }

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)

                if (account == null) {
                    isLoading = false
                    errorText = Constants.Message.GOOGLE_SIGN_IN_FAILED_MESSAGE
                } else {
                    account.idToken?.let { token ->
                        errorText = null
                        val credential = GoogleAuthProvider.getCredential(token, null)
                        authViewModel.signInWithGoogle(credential)
                    }
                }
            } catch (e: ApiException) {
                errorText = Constants.Message.GOOGLE_SIGN_IN_FAILED_MESSAGE
            }
        }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AuthView(
                modifier = Modifier.fillMaxSize(),
                isLoading = isLoading,
                errorText = errorText,
                onClick = {
                    isLoading = true
                    errorText = null
                    authResultLauncher.launch(singInRequestCode)
                }
            )
        }
    }
}

@Composable
fun AuthView(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    errorText: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalSpacer(size = 40)
        Image(
            modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
            painter = painterResource(id = R.drawable.google),
            contentDescription = null
        )

        SignInButton(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = stringResource(R.string.g_sign_in),
            icon = painterResource(id = R.drawable.google),
            isLoading = isLoading,
            onClick = onClick
        )

        AnimatedVisibility(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.CenterHorizontally),
            visible = !isLoading
        ) {
            errorText?.let {
                ErrorText(text = it)
            }
        }

        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            text = stringResource(id = R.string.privacy_policy_and_terms_of_usage),
            style = MaterialTheme.typography.titleSmall
        )
    }
}
