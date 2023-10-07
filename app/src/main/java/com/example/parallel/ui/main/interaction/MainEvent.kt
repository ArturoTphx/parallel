package com.example.parallel.ui.main.interaction

/**
 * Sealed class that helps to the code encapsulation between the MainViewModel
 * and the UI.
 * It contains all the events that can occur from the UI to the MainViewModel.
 */
sealed class MainEvent {
    data class Run(val dataAmount: Int): MainEvent()
    object ShowUi: MainEvent()
    object Refresh: MainEvent()
    object ResetAll: MainEvent()
}
