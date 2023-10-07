package com.example.parallel.domain.notes

import com.example.parallel.data.notes.NoteRepository
import javax.inject.Inject

/**
 * Use case in charge invoking the note repository to clean all the notes of the mono threading
 * and multi threading files.
 * It needs a NotesRepository object to access all its public methods.
 */
class CleanNotes @Inject constructor(private val notesRepository: NoteRepository) {
    operator fun invoke() {
        notesRepository.cleanNotes()
    }
}