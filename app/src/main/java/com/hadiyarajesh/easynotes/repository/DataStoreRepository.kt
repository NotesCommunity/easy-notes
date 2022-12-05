package com.hadiyarajesh.easynotes.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hadiyarajesh.easynotes.utility.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "${Constants.App.APP_NAME.lowercase()}_prefs"
)

class DataStoreRepository @Inject constructor(@ApplicationContext val context: Context) {
    companion object {
        val APP_LANGUAGE_KEY = stringPreferencesKey(name = "app_language")
        val AUTHENTICATED = booleanPreferencesKey(name = "authenticated")
    }

    val appLanguage: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[APP_LANGUAGE_KEY]
        }

    val authenticated: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[AUTHENTICATED] ?: false
        }

    suspend fun saveAppLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[APP_LANGUAGE_KEY] = language
        }
    }

    suspend fun saveUserSignedIn(isSigned: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTHENTICATED] = isSigned
        }
    }
}