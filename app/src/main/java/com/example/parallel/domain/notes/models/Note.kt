package com.example.parallel.domain.notes.models

import com.example.parallel.data.notes.models.NoteItem

// This is the note model in the domain layer
data class Note(
    val id: String = System.currentTimeMillis().toString(),
    val title: String = "",
    val body: String = ""
)

// Mappers responsible of transforming a note object between layers as needed
fun NoteItem.toNote() = Note(id = id, title = title, body = body)
fun Note.toNoteItem() = NoteItem(id = id, title = title, body = body)
