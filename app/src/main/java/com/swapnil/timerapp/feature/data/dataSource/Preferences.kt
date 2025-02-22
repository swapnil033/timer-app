package com.swapnil.timerapp.feature.data.dataSource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "timer_prefs")

object PreferencesKeys {
    val END_TIME = longPreferencesKey("end_time")
}