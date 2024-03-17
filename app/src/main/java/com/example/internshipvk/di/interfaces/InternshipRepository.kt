package com.example.internshipvk.di.interfaces

import android.app.Activity
import androidx.credentials.PasswordCredential

interface InternshipRepository {

    suspend fun getFavIcon(url:String):String

    suspend fun getCredential(
        activity: Activity,
        username:String?
    ): PasswordCredential?

    suspend fun saveCredential(
        activity: Activity,
        login:String,
        password:String
    ):Boolean
}