package com.hadiyarajesh.easynotes.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    fun signInWithGoogle(credential: AuthCredential) =viewModelScope.launch {
        try{
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)

        }catch (e:Exception){
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }
}