package com.example.parallel.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parallel.ui.components.MyNavDrawer
import com.example.parallel.ui.components.MyTopBar
import com.example.parallel.ui.main.viewmodels.MainViewModel
import com.example.parallel.ui.main.views.BenchmarksScreen
import com.example.parallel.ui.main.views.NotesScreen
import com.example.parallel.ui.main.views.SettingsScreen
import com.example.parallel.ui.theme.ParallelTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParallelTheme {

                mainViewModel = hiltViewModel()

                val navHostController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                Column(modifier = Modifier.fillMaxSize()) {
                    MyNavDrawer(
                        scope = coroutineScope,
                        drawerState = drawerState,
                        navHostController = navHostController,
                        items = listOf(
                            MainDestination.Notes,
                            MainDestination.Benchmarks,
                            MainDestination.Settings
                        )
                    ) {
                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize(),
                            containerColor = MaterialTheme.colorScheme.background,
                            topBar = {
                                MyTopBar(
                                    onOpenDrawer = {
                                        coroutineScope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() }
                                    }
                                )
                            }
                        ) {
                            Column(modifier = Modifier.padding(it)) {
                                NavHost(
                                    navController = navHostController,
                                    startDestination = MainDestination.Benchmarks.route
                                ) {
                                    composable(route = MainDestination.Benchmarks.route) {
                                        BenchmarksScreen(
                                            mainState = mainViewModel.state.value,
                                            onViewModelInteraction = { event ->
                                                mainViewModel.onEvent(
                                                    event
                                                )
                                            }
                                        )
                                    }
                                    composable(route = MainDestination.Notes.route) {
                                        NotesScreen(
                                            mainState = mainViewModel.state.value,
                                            onViewModelInteraction = { event ->
                                                mainViewModel.onEvent(
                                                    event
                                                )
                                            }
                                        )
                                    }
                                    composable(route = MainDestination.Settings.route) {
                                        SettingsScreen(
                                            mainState = mainViewModel.state.value,
                                            onViewModelInteraction = { event ->
                                                mainViewModel.onEvent(
                                                    event
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
