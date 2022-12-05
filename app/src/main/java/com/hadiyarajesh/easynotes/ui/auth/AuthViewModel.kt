package com.hadiyarajesh.easynotes.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hadiyarajesh.easynotes.utility.PreferenceManager
import com.hadiyarajesh.easynotes.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    private val _authState: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Empty)
    val authState: StateFlow<UiState<String>> = _authState.asStateFlow()

    fun signInWithGoogle(credential: AuthCredential) = viewModelScope.launch {
        try {
            _authState.value = UiState.Loading
            Firebase.auth.signInWithCredential(credential).await()
            saveUserSignedIn()
            _authState.value = UiState.Success("Logged in successfully")
        } catch (e: Exception) {
            _authState.value = UiState.Error(e.localizedMessage)
        }
    }

    private suspend fun saveUserSignedIn() {
        preferenceManager.saveUserSignedIn(true)
    }
}
