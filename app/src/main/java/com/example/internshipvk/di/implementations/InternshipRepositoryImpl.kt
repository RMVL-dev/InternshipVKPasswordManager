package com.example.internshipvk.di.implementations

import android.app.Activity
import android.app.Application
import android.credentials.CreateCredentialException
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import com.example.internshipvk.di.interfaces.InternshipRepository
import com.example.internshipvk.network.Service

class InternshipRepositoryImpl(
    private val service: Service,
    private val application: Application
):InternshipRepository {

    private val credentialManager by lazy {
        CredentialManager.create(application)
    }

    /**
     * метод получения разметки html страницы, из нее забирается favarite icon
     */
    override suspend fun getFavIcon(url: String):String = service.getFavIcon(url)


    /**
     * Метод получения логина и пароля из хранилища паролей
     */
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun getCredential(
        activity: Activity,
        username:String?
    ): PasswordCredential?{
        if (username == null)
            return null

        return try {
            val getCredentialRequest = GetCredentialRequest(
                listOf(GetPasswordOption(allowedUserIds = setOf(username)))
            )
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = getCredentialRequest
            )
            credentialResponse.credential as? PasswordCredential
        }catch (e: CreateCredentialException){
            e.printStackTrace()
            null
        }

    }


    /**
     * Метод сохранения паролей в хранилище
     */
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun saveCredential(
        activity: Activity,
        login:String,
        password:String,
    ):Boolean{
        return try {
            credentialManager.createCredential(
                request = CreatePasswordRequest(login, password),
                context = activity
            )
            true
        }catch (e:CreateCredentialException){
            e.printStackTrace()
            false
        }catch (e: CreateCredentialCancellationException){
            e.printStackTrace()
            false
        }
    }

}