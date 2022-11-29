package com.hadiyarajesh.easynotes.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hadiyarajesh.easynotes.utility.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val preferenceManager: PreferenceManager) : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    val userSignedIn = preferenceManager.isUserSignedIn.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun signInWithGoogle(credential: AuthCredential) =viewModelScope.launch {
        try{
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            saveUserSignedIn()
            loadingState.emit(LoadingState.LOADED)

        }catch (e:Exception){
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    private suspend fun saveUserSignedIn() {
        preferenceManager.saveUserSignedIn(true)
    }

}