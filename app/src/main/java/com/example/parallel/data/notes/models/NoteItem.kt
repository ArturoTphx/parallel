package com.example.parallel.data.notes.models

// Simple note model in the data layer
data class NoteItem(
    val id: String = "",
    val title: String = "",
    val body: String = ""
) {
    // Serializer used to store the notes in a file
    override fun toString(): String {
        return "|id:=${id}||title:=${title}||body:=${body}|\n"
    }
}
