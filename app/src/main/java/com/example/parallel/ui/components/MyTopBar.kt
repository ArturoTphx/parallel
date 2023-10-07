package com.example.parallel.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.parallel.ui.theme.ParallelTheme
import com.example.parallel.utils.CustomPreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    onOpenDrawer: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onOpenDrawer) {
                Icon(Icons.Filled.Menu, contentDescription = "MENU")
            }
        }
    )
}

@CustomPreviews
@Composable
fun TopBarPreview() {
    ParallelTheme {
        MyTopBar(
            onOpenDrawer = {}
        )
    }
}