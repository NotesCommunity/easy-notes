package com.hadiyarajesh.easynotes.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.hadiyarajesh.easynotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteTopBar(
    onBackClick: () -> Unit,
    createNoteClick: () -> Unit,
    title: String,

) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = createNoteClick) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
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
