package com.hadiyarajesh.easynotes.ui.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.ui.components.SignInButton

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthScreen() {
    var isLoading by remember { mutableStateOf(false) }

    AuthView(isLoading) {
        isLoading = !isLoading
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
                    onClick
                })
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}