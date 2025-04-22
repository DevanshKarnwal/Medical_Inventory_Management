package com.example.pref

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "user_preferences")
class PreferencesDataStore(private val context : Context){
    companion object{
        // key
        private val User_ID_KEY = stringPreferencesKey("user_id")

    }
    val PrefUserid : Flow<String> = context.dataStore.data.map {
        it[User_ID_KEY] ?: ""

    }
    suspend fun saveUserId(userId : String){
        context.dataStore.edit { prf ->
            prf[User_ID_KEY] = userId
        }
    }
}