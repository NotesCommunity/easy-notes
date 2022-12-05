package com.hadiyarajesh.easynotes.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.hadiyarajesh.easynotes.BuildConfig

class AuthResultContract : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, signInOptions)
            .signInIntent
            .putExtra("input", input)
    }


    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        return when (resultCode) {
            Activity.RESULT_OK -> GoogleSignIn.getSignedInAccountFromIntent(intent)

            else -> {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Google Auth ResultCode: $resultCode")

                    val bundle = intent?.extras
                    bundle?.keySet()?.forEach {
                        Log.d(TAG, "Google Auth Data: ${bundle.get(it)}")
                    }
                }

                null
            }
        }
    }
}
