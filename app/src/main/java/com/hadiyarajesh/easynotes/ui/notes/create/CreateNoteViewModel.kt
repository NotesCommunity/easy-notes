package com.hadiyarajesh.easynotes.ui.notes.create

import androidx.lifecycle.ViewModel
import com.hadiyarajesh.easynotes.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    
}
