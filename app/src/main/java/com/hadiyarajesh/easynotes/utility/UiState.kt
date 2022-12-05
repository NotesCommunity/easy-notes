package com.hadiyarajesh.easynotes.utility

/**
 * This class re-present state of UI
 * Empty, Loading, Success or Error
 * So we can drive the UI based on the state
 */
sealed class UiState<out T> {
    object Empty : UiState<Nothing>()

    object Loading : UiState<Nothing>()

    data class Success<out T>(val data: T) : UiState<T>()

    data class Error(val data: String?) : UiState<Nothing>()
}

infix fun <T> UiState<T>.takeIfSuccess(onSuccess: UiState.Success<T>.() -> Unit): UiState<T> {
    return when (this) {
        is UiState.Success -> {
            onSuccess(this)
            this
        }

        else -> {
            this
        }
    }
}

infix fun <T> UiState<T>.takeIfError(onError: UiState.Error.() -> Unit): UiState<T> {
    return when (this) {
        is UiState.Error -> {
            onError(this)
            this
        }

        else -> {
            this
        }
    }
}
