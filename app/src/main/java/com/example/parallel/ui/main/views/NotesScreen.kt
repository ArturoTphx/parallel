package com.example.parallel.ui.main.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parallel.R
import com.example.parallel.ui.components.NoteList
import com.example.parallel.ui.main.interaction.MainEvent
import com.example.parallel.ui.main.interaction.MainState
import com.example.parallel.ui.theme.ParallelTheme
import com.example.parallel.utils.Constants
import com.example.parallel.utils.CustomPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(mainState: MainState, onViewModelInteraction: (MainEvent) -> Unit) {

    val monoNotes = mainState.monoNotes
    val multiNotes = mainState.multiNotes

    val isMulti = remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onViewModelInteraction(MainEvent.Refresh) }
            ) {
                Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "Refresh")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = isMulti.value,
                    onCheckedChange = { checked->
                        isMulti.value = checked
                    }
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(stringResource(id = R.string.multi))
            }

            Text(
                text = stringResource(
                    id = R.string.length_info,
                    if (isMulti.value) multiNotes.size else monoNotes.size
                )
            )

            if (mainState.showUi) {
                NoteList(notes = if (isMulti.value) multiNotes else monoNotes)
            } else {
                Text(stringResource(R.string.note_cards_message))
            }
        }
    }

}

@CustomPreviews
@Composable
fun NotesPreview() {
    ParallelTheme {
        NotesScreen(
            mainState = MainState(
                showUi = true,
                monoNotes = Constants.FAKE_NOTES,
                multiNotes = Constants.FAKE_NOTES
            ),
            onViewModelInteraction = {}
        )
    }
}