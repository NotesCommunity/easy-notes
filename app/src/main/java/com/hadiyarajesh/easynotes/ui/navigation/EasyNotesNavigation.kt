package com.hadiyarajesh.easynotes.ui.navigation

//import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hadiyarajesh.easynotes.ui.auth.AuthScreen
import com.hadiyarajesh.easynotes.ui.auth.AuthViewModel
import com.hadiyarajesh.easynotes.ui.notes.NotesScreen
import com.hadiyarajesh.easynotes.ui.notes.NotesViewModel
import com.hadiyarajesh.easynotes.ui.profile.ProfileScreen
import com.hadiyarajesh.easynotes.ui.profile.ProfileViewModel
import com.hadiyarajesh.easynotes.ui.settings.SettingsScreen
import com.hadiyarajesh.easynotes.ui.settings.SettingsViewModel

@Composable
fun EasyNotesNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TopLevelDestination.Auth.route
    ) {

        composable(route = TopLevelDestination.Notes.route) {
            bottomBarState.value = true
            val notesViewModel = hiltViewModel<NotesViewModel>()

            NotesScreen(
                navController = navController,
                notesViewModel = notesViewModel
            )
        }

        composable(route = TopLevelDestination.Profile.route) {
            bottomBarState.value = true
            val profileViewModel = hiltViewModel<ProfileViewModel>()

            ProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }

        composable(route = TopLevelDestination.Settings.route) {
            bottomBarState.value = false
            val settingsViewModel = hiltViewModel<SettingsViewModel>()

            SettingsScreen(
                navController = navController,
                settingsViewModel = settingsViewModel
            )
        }

        composable(route = TopLevelDestination.Auth.route){
            bottomBarState.value = false
            val authViewModel = hiltViewModel<AuthViewModel>()

            AuthScreen(navController = navController, authViewModel)
        }
    }
}

//@Composable
//fun MainBottomBar(
//    navController: NavController,
//    items: List<TopLevelDestination>
//) {
//    BottomNavigation(
//        modifier = Modifier.height(50.dp),
//        backgroundColor = MaterialTheme.colors.background,
//        contentColor = contentColorFor(MaterialTheme.colors.background)
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//
//        items.forEach { screen ->
//            val selected = navBackStackEntry?.destination?.route == screen.route
//            val icon = if (selected) screen.selectedIcon else screen.unselectedIcon
//
//            BottomNavigationItem(
//                icon = {
//                    Icon(
//                        modifier = Modifier.iconSize(),
//                        painter = painterResource(id = icon),
//                        contentDescription = screen.route
//                    )
//                },
//                selected = selected,
//                onClick = {
//                    navController.navigate(screen.route) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                label = { Text(text = screen.route) },
//                alwaysShowLabel = false
//            )
//        }
//    }
//}
