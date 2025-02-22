package com.swapnil.timerapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.swapnil.timerapp.feature.presentation.timer_page.TimerPageScreenRoot
import com.swapnil.timerapp.feature.presentation.timer_page.TimerPageViewModel
import com.swapnil.timerapp.feature.presentation.timer_page.TimerPageViewModelFactory
import com.swapnil.timerapp.ui.theme.TimerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            101
        )

        setContent {
            TimerAppTheme {

                val viewModel by viewModels<TimerPageViewModel> {
                    TimerPageViewModelFactory(application)
                }

                TimerPageScreenRoot(
                    viewModel = viewModel
                )
            }
        }
    }
}