package com.luminor.luminortestproject.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionManager(private val context: Context) {

    companion object {
        private val KEY_LOGGED_IN_EMAIL = stringPreferencesKey("logged_in_email")
    }

    val loggedInEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_LOGGED_IN_EMAIL]
    }

    suspend fun saveLogin(email: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LOGGED_IN_EMAIL] = email
        }
    }

    suspend fun clearLogin() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_LOGGED_IN_EMAIL)
        }
    }
}
