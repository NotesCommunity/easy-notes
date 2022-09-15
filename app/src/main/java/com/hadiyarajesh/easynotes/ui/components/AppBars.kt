package com.hadiyarajesh.easynotes.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hadiyarajesh.easynotes.R

@Composable
fun NotesTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) }
    )
}

@Composable
fun ProfileTopBar(
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.profile)) },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        }
    )
}

@Composable
fun SettingsTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.settings)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}
