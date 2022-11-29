package com.hadiyarajesh.easynotes.ui.createnote

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.hadiyarajesh.easynotes.ui.components.CreateUpdateNote


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    darkTheme: Boolean = isSystemInDarkTheme(),
    navController: NavController,
    createNoteViewModel: CreateNoteViewModel
) {
    var isUpdate = false
    CreateUpdateNote(isUpdate, darkTheme, navController, createNoteViewModel)
}


