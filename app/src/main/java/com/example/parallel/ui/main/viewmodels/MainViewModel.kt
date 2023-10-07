package com.example.parallel.ui.main.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parallel.domain.notes.CleanNotes
import com.example.parallel.domain.notes.GetNotes
import com.example.parallel.domain.notes.InsertNotes
import com.example.parallel.domain.notes.models.Benchmark
import com.example.parallel.domain.notes.models.Note
import com.example.parallel.ui.main.interaction.MainEvent
import com.example.parallel.ui.main.interaction.MainState
import com.example.parallel.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel that contains all the needed logic of the MainActivity.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertNotes: InsertNotes,
    private val getNotes: GetNotes,
    private val cleanNotes: CleanNotes
) : ViewModel() {

    // One mutable private state that can only be modified by the MainViewModel.
    private val _state: MutableState<MainState> = mutableStateOf(MainState())
    // One public immutable state that gets it's data from the private one.
    // This state is shared with the UI.
    val state: State<MainState> get() = _state

    // Runs when a MainViewModel instance is created.
    init {
        deleteNotes()
    }

    /**
     * This is the only public function of the MainViewModel, and it's in charge of managing the flow of events of the UI based on what the user wants.
     */
    fun onEvent(mainEvent: MainEvent) {
        when (mainEvent) {
            is MainEvent.Run -> {
                run(mainEvent.dataAmount)
            }
            is MainEvent.Refresh -> {
                collectMonoNotes()
                collectMultiNotes()
            }
            is MainEvent.ShowUi -> {
                toggleUi()
            }
            is MainEvent.ResetAll -> {
                resetAll()
            }
        }
    }

    /**
     * Switch the "Show note cards" setting based on user input.
     * This enables or disables the list of note cards in the UI to save memory.
     */
    private fun toggleUi() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value.copy(showUi = !_state.value.showUi)
        }
    }

    /**
     * Cleans all the notes in the files and restart all the benchmark results.
     */
    private fun resetAll() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoading()
            cleanNotes()
            setBenchmarks(emptyList())
            collectMonoNotes()
            collectMultiNotes()
            unSetLoading()
        }
    }

    /**
     * Cleans all the notes in the files.
     */
    private fun deleteNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoading()
            cleanNotes()
            unSetLoading()
        }
    }

    /**
     * The next two functions are in charge of requesting mono threading and multi threading notes.
     */
    private fun collectMonoNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoading()
            val monoNotes = getNotes(Constants.MONO_THREAD)
            setMonoNotes(monoNotes)
            unSetLoading()
        }
    }

    private fun collectMultiNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoading()
            val multiNotes = getNotes(Constants.MULTI_THREAD)
            setMultiNotes(multiNotes)
            unSetLoading()
        }
    }

    /**
     * Starts the benchmark for each threading type, and when finished, it append
     * the benchmark result to the table.
     */
    private fun run(dataAmount: Int) {
        viewModelScope.launch {
            setLoading()

            val mono = async { start(dataAmount, Constants.MONO_THREAD) }
            val multi = async { start(dataAmount, Constants.MULTI_THREAD) }

            mono.await()
            multi.await()

            val bench = Benchmark(
                dataAmount = dataAmount,
                monoSecs = state.value.monoSecs,
                multiSecs = state.value.multiSecs
            )

            val newList = ArrayList<Benchmark>(state.value.benchmarks)

            newList.add(bench)

            setBenchmarks(benchmarks = newList)

            collectMonoNotes()
            collectMultiNotes()

            unSetLoading()
        }
    }

    /**
     * Measure the time that each threading time takes to insert a N (dataAmount) of notes.
     */
    private suspend fun start(dataAmount: Int, typeOfThreading: String) {
        val dispatcher =
            if (typeOfThreading == Constants.MONO_THREAD) Dispatchers.Main else Dispatchers.IO

        viewModelScope.launch(dispatcher) {

            val startTimestamp = System.currentTimeMillis()

            val notes = ArrayList<Note>()

            for (i in 1..dataAmount) {
                val millis = System.currentTimeMillis().toString()
                val id = i.toString() + millis
                notes.add(Note(id = id, title = id, body = id))
            }

            val task = async { insertNotes(typeOfThreading, notes) }

            task.await()

            val endTimestamp = System.currentTimeMillis()

            val seconds = (endTimestamp - startTimestamp)

            val fSeconds = seconds.toFloat()

            if (typeOfThreading == Constants.MONO_THREAD) {
                setMonoSecs(fSeconds)
            } else {
                setMultiSecs(fSeconds)
            }
        }
    }

    /**
     * All the following functions are in charge of modifying the private state of the MainViewModel.
     */
    private fun setMonoSecs(secs: Float) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value.copy(monoSecs = secs)
        }
    }

    private fun setMultiSecs(secs: Float) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value.copy(multiSecs = secs)
        }
    }

    private fun setBenchmarks(benchmarks: List<Benchmark>) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value.copy(benchmarks = benchmarks)
        }
    }

    private fun setMonoNotes(notes: List<Note>) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value.copy(monoNotes = notes)
        }
    }

    private fun setMultiNotes(notes: List<Note>) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value.copy(multiNotes = notes)
        }
    }

    private fun setLoading() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value.copy(loading = true)
        }
    }

    private fun unSetLoading() {
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value.copy(loading = false)
        }
    }
}