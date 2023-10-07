package com.example.parallel.ui.main.interaction

import com.example.parallel.domain.notes.models.Benchmark
import com.example.parallel.domain.notes.models.Note

/**
 * Data class in charge of saving the state of the MainViewModel.
 * It contains all the data that needs to be shared between the MainViewModel
 * and the UI.
 */
data class MainState(
    val showUi: Boolean = false,
    val monoNotes: List<Note> = emptyList(),
    val multiNotes: List<Note> = emptyList(),
    val loading: Boolean = false,
    val monoSecs: Float = 0f,
    val multiSecs: Float = 0f,
    val benchmarks: List<Benchmark> = emptyList()
)
