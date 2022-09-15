package com.hadiyarajesh.easynotes.di

import android.content.Context
import androidx.room.Room
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.database.AppDatabase
import com.hadiyarajesh.easynotes.database.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "${context.getString(R.string.app_name)}.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: AppDatabase): NoteDao = db.noteDao
}
