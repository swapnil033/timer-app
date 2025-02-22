package com.swapnil.timerapp.feature.presentation.timer_page

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swapnil.timerapp.feature.data.dataSource.TimerDataStore
import com.swapnil.timerapp.feature.data.repositories.TimerRepositoryImpl

class TimerPageViewModelFactory(
    private val context: Application
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")

            val dataStore = TimerDataStore(context)
            val repository = TimerRepositoryImpl(dataStore)

            return TimerPageViewModel(
                context = context
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}