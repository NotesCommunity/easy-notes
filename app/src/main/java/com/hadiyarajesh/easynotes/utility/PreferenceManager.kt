package com.hadiyarajesh.easynotes.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "${Constants.App.APP_NAME.lowercase()}_prefs"
)

class PreferenceManager @Inject constructor(@ApplicationContext val context: Context) {
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

    val isUserSignedIn: Flow<Boolean?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[AUTHENTICATED]
        }

    suspend fun saveAppLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[APP_LANGUAGE_KEY] = language
        }
    }

    suspend fun saveUserSignedIn(isSigned : Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTHENTICATED] = isSigned
        }
    }
}
