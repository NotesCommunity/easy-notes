package com.hadiyarajesh.easynotes.ui.createnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hadiyarajesh.easynotes.database.entity.Note
import com.hadiyarajesh.easynotes.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    
}
