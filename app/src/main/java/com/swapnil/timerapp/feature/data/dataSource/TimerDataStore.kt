package com.swapnil.timerapp.feature.data.dataSource

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.swapnil.timerapp.feature.domain.models.TimeType

@Deprecated("out dated way")
class TimerDataStore(
    context: Context
) {

    companion object{
        const val HOUR = "HOUR"
        const val MINUTE = "MINUTE"
        const val SECOND = "SECOND"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("TimerPreferences", Context.MODE_PRIVATE)

    fun saveTime(timeType: TimeType, value: String){
        val key = timeType.name

        sharedPreferences.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getTime(timeType: TimeType): String{
        val key = timeType.name
        return sharedPreferences.getString(key, "") ?: ""
    }

}