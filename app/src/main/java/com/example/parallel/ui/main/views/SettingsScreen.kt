package com.example.parallel.ui.main.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parallel.R
import com.example.parallel.ui.main.interaction.MainEvent
import com.example.parallel.ui.main.interaction.MainState
import com.example.parallel.ui.theme.ParallelTheme
import com.example.parallel.utils.CustomPreviews

@Composable
fun SettingsScreen(
    mainState: MainState,
    onViewModelInteraction: (MainEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = mainState.showUi,
                onCheckedChange = {
                    onViewModelInteraction(MainEvent.ShowUi)
                }
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(stringResource(R.string.show_cards))
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (mainState.loading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = { onViewModelInteraction(MainEvent.ResetAll) }) {
                Text(text = stringResource(R.string.reset_all))
            }
        }
    }
}

@CustomPreviews
@Composable
fun SettingsPreview() {
    ParallelTheme {
        SettingsScreen(mainState = MainState(), onViewModelInteraction = {})
    }
}