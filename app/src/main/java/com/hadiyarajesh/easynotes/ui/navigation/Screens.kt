package com.hadiyarajesh.easynotes.ui.navigation

import androidx.annotation.DrawableRes
import com.hadiyarajesh.easynotes.R

sealed class Screens(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
) {
    object Notes : Screens(
        route = "Notes",
        icon = R.drawable.ic_note_outlined,
        selectedIcon = R.drawable.ic_note_filled
    )

    object Profile : Screens(
        route = "Profile",
        icon = R.drawable.ic_user_oulined,
        selectedIcon = R.drawable.ic_user_filled
    )

    object Settings : Screens(
        route = "Settings",
        icon = R.drawable.ic_settings_outlined,
        selectedIcon = R.drawable.ic_settings_outlined
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
    Screens.Notes,
    Screens.Profile
)
