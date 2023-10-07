package com.example.parallel.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.parallel.R

/**
 * Sealed class in charge of storing all the possible destinations of the MainActivity.
 */
sealed class MainDestination(val route: String, @StringRes val name: Int, val icon: ImageVector) {
    object Notes: MainDestination(route = "notes", name = R.string.notes_destination, icon = Icons.Filled.Notes)
    object Benchmarks: MainDestination(route = "benchmarks", name = R.string.benchmarks_destination, icon = Icons.Filled.Analytics)
    object Settings: MainDestination(route = "settings", name = R.string.settings_destination, icon = Icons.Filled.Settings)
}
