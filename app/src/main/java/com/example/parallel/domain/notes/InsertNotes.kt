package com.example.parallel.domain.notes

import com.example.parallel.data.notes.NoteRepository
import com.example.parallel.domain.notes.models.Note
import com.example.parallel.domain.notes.models.toNoteItem
import com.example.parallel.utils.Constants
import javax.inject.Inject

/**
 * Use case in charge invoking the note repository to insert notes to the mono threading
 * or multi threading files.
 * It needs a NotesRepository object to access all its public methods.
 */
class InsertNotes @Inject constructor(private val noteRepository: NoteRepository) {
    operator fun invoke(typeOfThreading: String, notes: List<Note>) {
        if (typeOfThreading == Constants.MULTI_THREAD) {
            noteRepository.multiInsertNote(notes.map { it.toNoteItem() })
        } else {
            noteRepository.monoInsertNote(notes.map { it.toNoteItem() })
        }
    }
}