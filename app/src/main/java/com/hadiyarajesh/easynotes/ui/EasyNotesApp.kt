package com.hadiyarajesh.easynotes.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hadiyarajesh.easynotes.ui.navigation.EasyNotesNavigation
import com.hadiyarajesh.easynotes.ui.navigation.MainBottomBar
import com.hadiyarajesh.easynotes.ui.navigation.bottomNavItems
import com.hadiyarajesh.easynotes.ui.theme.EasyNotesTheme

@Composable
fun EasyNotesApp() {
    EasyNotesTheme {
        val navController = rememberNavController()
        // A state that maintains visibility of a bottom bar
        val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState.value,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
                    MainBottomBar(
                        navController = navController,
                        items = bottomNavItems
                    )
                }
            }
        ) { innerPadding ->
            EasyNotesNavigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                bottomBarState = bottomBarState
            )
        }
    }
}
