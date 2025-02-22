package com.swapnil.timerapp.feature.presentation.timer_page

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swapnil.timerapp.feature.presentation.components.TimerTextField
import com.swapnil.timerapp.feature.presentation.services.TimerService
import kotlinx.coroutines.flow.onEach

@Composable
fun TimerPageScreenRoot(
    viewModel: TimerPageViewModel,
) {

    val state by viewModel.state.collectAsState()

    TimerPageScreen(
        state = state, onAction = viewModel::onAction
    )
}

@Composable
private fun TimerPageScreen(
    state: TimerPageState, onAction: (TimerPageAction) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            if(state.isTimerRunning){
                Text(
                    text = state.timer,
                    style = MaterialTheme.typography.displayLarge
                )
            }else{
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.hour.let {
                        TimerTextField(
                            value = it.value,
                            onValueChange = { onAction(TimerPageAction.OnHourChange(it)) },
                            label = it.label,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.width(10.dp))

                    state.minute.let {
                        TimerTextField(
                            value = it.value,
                            onValueChange = { onAction(TimerPageAction.OnMinuteChange(it)) },
                            label = it.label,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.width(10.dp))

                    state.second.let {
                        TimerTextField(
                            value = it.value,
                            onValueChange = { onAction(TimerPageAction.OnSecondChange(it)) },
                            label = it.label,
                            modifier = Modifier.weight(1f)
                        )
                    }

                }
            }

            Spacer(Modifier.height(40.dp))

            val buttonText = when(state.isTimerRunning){
                true -> "Stop"
                false -> "Start"
            }


            val context = LocalContext.current

            Button(onClick = {

                onAction(
                    when(state.isTimerRunning){
                        true -> {

                            TimerPageAction.OnStop(context)
                        }
                        false -> {

                            TimerPageAction.OnStart(context)
                        }
                    }
                )
            }) {
                Text(text = buttonText)
            }

        }
    }
}


@Preview
@Composable
private fun TimerPageScreenPreview() {
    MaterialTheme {
        TimerPageScreen(state = TimerPageState(), onAction = {})
    }
}
@Preview
@Composable
private fun TimerPageScreenPreview2() {
    MaterialTheme {
        TimerPageScreen(state = TimerPageState(isTimerRunning = true), onAction = {})
    }
}