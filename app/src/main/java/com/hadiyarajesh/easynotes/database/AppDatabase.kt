package com.hadiyarajesh.easynotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hadiyarajesh.easynotes.database.converter.InstantConverter
import com.hadiyarajesh.easynotes.database.converter.ListConverter
import com.hadiyarajesh.easynotes.database.dao.NoteDao
import com.hadiyarajesh.easynotes.database.entity.Note

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        Note::class
    ]
)
@TypeConverters(
    InstantConverter::class,
    ListConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}
