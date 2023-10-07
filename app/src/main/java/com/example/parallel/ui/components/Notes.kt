package com.example.parallel.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.parallel.domain.notes.models.Note
import com.example.parallel.ui.theme.ParallelTheme
import com.example.parallel.utils.Constants
import com.example.parallel.utils.CustomPreviews

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<Note>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 175.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(items = notes) { note ->
            NoteCard(
                modifier = Modifier
                    .padding(5.dp)
                    .height(100.dp)
                    .width(150.dp),
                note = note
            )
        }
    }
}

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = note.title, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = note.body, maxLines = 3, overflow = TextOverflow.Ellipsis)
        }
    }
}

@CustomPreviews
@Composable
fun NoteCardPreview() {
    ParallelTheme {
        NoteCard(note = Constants.FAKE_NOTES[0])
    }
}

@CustomPreviews
@Composable
fun NoteListPreview() {
    ParallelTheme {
        NoteList(notes = Constants.FAKE_NOTES)
    }
}