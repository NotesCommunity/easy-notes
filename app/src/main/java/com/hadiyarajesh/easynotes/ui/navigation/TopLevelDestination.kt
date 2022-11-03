package com.hadiyarajesh.easynotes.ui.navigation

import androidx.annotation.DrawableRes
import com.hadiyarajesh.easynotes.R

sealed class TopLevelDestination(
    val title: String,
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int
) {
    object Notes : TopLevelDestination(
        title = "Notes",
        route = "all_notes",
        selectedIcon = R.drawable.ic_note_filled,
        unselectedIcon = R.drawable.ic_note_outlined
    )

    object Profile : TopLevelDestination(
        title = "Profile",
        route = "profile",
        selectedIcon = R.drawable.ic_user_filled,
        unselectedIcon = R.drawable.ic_user_oulined
    )

    object Settings : TopLevelDestination(
        title = "Settings",
        route = "settings",
        selectedIcon = R.drawable.ic_settings_outlined,
        unselectedIcon = R.drawable.ic_settings_outlined
    )

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

val bottomNavItems = listOf(
    TopLevelDestination.Notes,
    TopLevelDestination.Profile
)
