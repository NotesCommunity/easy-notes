package com.hadiyarajesh.easynotes.ui.notes.create

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hadiyarajesh.easynotes.ui.components.CreateUpdateNote

@Composable
fun CreateNoteScreen(
    navController: NavController,
    createNoteViewModel: CreateNoteViewModel
) {
    CreateUpdateNote(
        isUpdate = false,
        navController = navController,
        createNoteViewModel = createNoteViewModel
    )
}
