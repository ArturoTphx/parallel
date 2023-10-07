package com.example.parallel.domain.notes

import com.example.parallel.data.notes.NoteRepository
import com.example.parallel.domain.notes.models.Note
import com.example.parallel.domain.notes.models.toNote
import com.example.parallel.utils.Constants
import javax.inject.Inject

/**
 * Use case in charge invoking the note repository to get all the notes of the mono threading
 * or multi threading files.
 * It needs a NotesRepository object to access all its public methods.
 */
class GetNotes @Inject constructor(private val noteRepository: NoteRepository) {
    operator fun invoke(typeOfThreading: String): List<Note> {
        return if (typeOfThreading == Constants.MULTI_THREAD) {
            noteRepository.multiGetNotes().map { it.toNote() }
        } else {
            noteRepository.monoGetNotes().map { it.toNote() }
        }
    }
}