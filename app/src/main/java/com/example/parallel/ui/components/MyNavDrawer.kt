@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.parallel.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parallel.ui.main.MainDestination
import com.example.parallel.ui.theme.ParallelTheme
import com.example.parallel.utils.CustomPreviews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyNavDrawer(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController,
    items: List<MainDestination>,
    content: @Composable () -> Unit
) {
    val selectedRoute = currentRoute(navHostController = navHostController)
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }
    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DismissibleDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.background) {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(stringResource(id = item.name)) },
                        selected = item.route == selectedRoute,
                        colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = MaterialTheme.colorScheme.tertiary, selectedIconColor = MaterialTheme.colorScheme.background, selectedTextColor = MaterialTheme.colorScheme.background),
                        onClick = {
                            navigate(navHostController, item, scope, drawerState)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        },
        content = content
    )
}

@Composable
fun currentRoute(navHostController: NavHostController): String? {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun navigate(navHostController: NavHostController, mainDestination: MainDestination, scope: CoroutineScope, drawerState: DrawerState) {
    navHostController.navigate(mainDestination.route) {
        launchSingleTop = true
    }
    if (drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }
}

@CustomPreviews
@Composable
fun NavDrawerPreview() {
    ParallelTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MyNavDrawer(
                scope = rememberCoroutineScope(),
                drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
                navHostController = rememberNavController(),
                items = listOf(
                    MainDestination.Notes,
                    MainDestination.Benchmarks,
                    MainDestination.Settings
                )
            ) {

            }
        }

    }
}