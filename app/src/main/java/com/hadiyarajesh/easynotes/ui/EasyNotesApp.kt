package com.hadiyarajesh.easynotes.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hadiyarajesh.easynotes.ui.auth.AuthScreen
import com.hadiyarajesh.easynotes.ui.navigation.EasyNotesNavigation
import com.hadiyarajesh.easynotes.ui.navigation.TopLevelDestination
import com.hadiyarajesh.easynotes.ui.navigation.bottomNavItems
import com.hadiyarajesh.easynotes.ui.theme.EasyNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EasyNotesApp() {
    EasyNotesTheme {
        val navController = rememberNavController()
        // A state that maintains visibility of a bottom bar
        val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

        AuthScreen()
        /*Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState.value,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
                    MainBottomBar(
                        destinations = bottomNavItems,
                        onNavigateToDestination = { destination ->
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        currentDestination = navController.currentBackStackEntryAsState().value?.destination
                    )
                }
            }
        ) { innerPadding ->
            EasyNotesNavigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                bottomBarState = bottomBarState
            )
        }*/
    }
}

@Composable
private fun MainBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }

                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = destination.route
                    )
                },
                label = { Text(destination.title) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false
