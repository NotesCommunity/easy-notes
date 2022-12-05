package com.hadiyarajesh.easynotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hadiyarajesh.easynotes.ui.home.HomeScreen
import com.hadiyarajesh.easynotes.ui.home.HomeViewModel
import com.hadiyarajesh.easynotes.ui.notes.NotesScreen
import com.hadiyarajesh.easynotes.ui.notes.NotesViewModel
import com.hadiyarajesh.easynotes.ui.notes.create.CreateNoteScreen
import com.hadiyarajesh.easynotes.ui.notes.create.CreateNoteViewModel
import com.hadiyarajesh.easynotes.ui.profile.ProfileScreen
import com.hadiyarajesh.easynotes.ui.profile.ProfileViewModel
import com.hadiyarajesh.easynotes.ui.search.SearchScreen
import com.hadiyarajesh.easynotes.ui.search.SearchViewModel
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
        startDestination = TopLevelDestination.Home.route
    ) {
        composable(route = TopLevelDestination.Home.route) {
            bottomBarState.value = true
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable(route = TopLevelDestination.Search.route) {
            bottomBarState.value = true
            val searchViewModel = hiltViewModel<SearchViewModel>()

            SearchScreen(
                navController = navController,
                searchViewModel = searchViewModel
            )
        }

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

        composable(route = TopLevelDestination.CreateNote.route) {
            bottomBarState.value = false
            val createNoteViewModel = hiltViewModel<CreateNoteViewModel>()

            CreateNoteScreen(
                navController = navController,
                createNoteViewModel = createNoteViewModel
            )
        }
    }
}
