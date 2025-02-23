package com.swapnil.timerapp.feature.presentation.timer_page

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swapnil.timerapp.feature.data.dataSource.TimerDataStore2
import com.swapnil.timerapp.feature.data.repositories.TimerRepository2Impl

class TimerPageViewModelFactory(
    private val context: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")

            val dataStore = TimerDataStore2(context)
            val repository = TimerRepository2Impl(dataStore)

            return TimerPageViewModel(
                context = context, timerRepository = repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}