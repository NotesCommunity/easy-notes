package com.hadiyarajesh.easynotes.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.hadiyarajesh.easynotes.database.entity.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(note: Note)

    @Query("SELECT * FROM Note WHERE noteId = :noteId")
    fun getById(noteId: Long): Note

    @Query("SELECT * FROM Note ORDER BY noteId DESC")
    fun getAllByDesc(): PagingSource<Int, Note>

    @Query("DELETE FROM Note WHERE noteId = :noteId")
    fun deleteById(noteId: Long)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM Note")
    fun deleteAll()
}
