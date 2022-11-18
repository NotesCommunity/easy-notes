package com.hadiyarajesh.easynotes.ui.auth

data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        IDLE
    }

    companion object{
        val LOADED = LoadingState(Status.SUCCESS)
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg : String?) = LoadingState(Status.FAILED)
    }
}
