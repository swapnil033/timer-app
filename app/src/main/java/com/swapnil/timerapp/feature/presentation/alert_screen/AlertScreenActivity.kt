package com.swapnil.timerapp.feature.presentation.alert_screen

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
import com.swapnil.timerapp.feature.presentation.util.notificationUtil.NotificationUtil
import com.swapnil.timerapp.ui.theme.TimerAppTheme

class AlertScreenActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val notificationId = intent.getIntExtra("notification_id", -1)

        setContent {


            val context = LocalContext.current

            TimerAppTheme {
                AlertScreen(
                    "Time to Roll",
                    onDismiss = {
                        if (notificationId != -1) {
                            NotificationUtil(context).cancelNotification(notificationId)
                        }
                        finish()
                    }
                )

            }
        }
    }
}
