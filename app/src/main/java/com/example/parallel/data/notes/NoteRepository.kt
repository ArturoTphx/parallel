package com.example.parallel.data.notes

import com.example.parallel.data.notes.local.FileEngine
import com.example.parallel.data.notes.local.FileParams
import com.example.parallel.data.notes.models.NoteItem
import javax.inject.Inject

/**
 * This is the repository of note models in the data layer, it is used by the domain layer
 * for making all the necessary use cases of the application.
 * It needs a FileEngine object to store all the data in files.
 */
class NoteRepository @Inject constructor(private val fileEngine: FileEngine) {

    fun cleanNotes() {
        fileEngine.resetFile(FileParams.Mono)
        fileEngine.resetFile(FileParams.Multi)
    }

    fun multiGetNotes(): List<NoteItem> {
        return fileEngine.readFromFile(FileParams.Multi)
    }

    fun monoGetNotes(): List<NoteItem> {
        return fileEngine.readFromFile(FileParams.Mono)
    }

    fun multiInsertNote(notes: List<NoteItem>) {
        fileEngine.loadToFile(notes, FileParams.Multi)
    }

    fun monoInsertNote(notes: List<NoteItem>) {
        fileEngine.loadToFile(notes, FileParams.Mono)
    }
}