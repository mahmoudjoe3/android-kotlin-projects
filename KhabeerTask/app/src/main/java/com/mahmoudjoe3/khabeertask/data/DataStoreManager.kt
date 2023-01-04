package com.mahmoudjoe3.khabeertask.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {
    private val appContext = context
    private val Context.dataStore: DataStore<Preferences>
    by preferencesDataStore(name = "data_store")

    companion object{
        private val KEY_AUTH = stringPreferencesKey("key_auth")
    }

    val authToken:Flow<String> = appContext.dataStore.data.map { preferences ->
        preferences[KEY_AUTH].toString()
    }

    suspend fun saveAuthToken(authToken: String){
        appContext.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
            Log.d("TAG", "saveAuthToken: $authToken")
        }
    }
}