package com.hadiyarajesh.easynotes.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.ui.components.ProfileTopBar
import com.hadiyarajesh.easynotes.ui.navigation.Screens

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
) {
    Scaffold(
        topBar = { ProfileTopBar(onSettingsClick = { navController.navigate(Screens.Settings.route) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.profile),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

