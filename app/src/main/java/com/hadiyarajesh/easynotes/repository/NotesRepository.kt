package com.hadiyarajesh.easynotes.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hadiyarajesh.easynotes.database.dao.NoteDao
import com.hadiyarajesh.easynotes.database.entity.Note
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getAllNotes(): Flow<PagingData<Note>> = Pager(
        config = PagingConfig(pageSize = 20)
    ) {
        noteDao.getAllByDesc()
    }.flow
}
