package com.example.internshipvk.ui.passwords

import android.app.Activity
import android.app.Application
import android.content.Context
import android.credentials.CreateCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipvk.InternshipApplication
import com.example.internshipvk.data.SiteData
import com.example.internshipvk.di.interfaces.InternshipRepository
import com.example.internshipvk.sharedPrefs.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.Exception

class PasswordsViewModel(
    application: InternshipApplication,
    repository: InternshipRepository
): AndroidViewModel(application) {


    private val credentialManager by lazy {
        CredentialManager.create(application)
    }

    var domainList:List<SiteData> by mutableStateOf(emptyList())
        private set

    lateinit var context:Context

    fun initDomainList(){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        domainList = try{
            Json.decodeFromString<List<SiteData>>(sharedPreferencesManager.siteList)
        }catch (e:Exception) {
            emptyList<SiteData>()
        }
    }

    private fun saveNewDomain(domain:String, login:String){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        if (!sharedPreferencesManager.siteList.isNullOrEmpty()) {
            val previousData =
                Json.decodeFromString<List<SiteData>>(sharedPreferencesManager.siteList)
                    .toMutableList()
            val newData: MutableList<SiteData> = previousData
            newData.add(SiteData(domain, login))
            sharedPreferencesManager.siteList = Json.encodeToString(newData)
            domainList = Json.decodeFromString<List<SiteData>>(sharedPreferencesManager.siteList)
        }else{
            sharedPreferencesManager.siteList = Json.encodeToString(listOf(SiteData(domain, login)))
            domainList = Json.decodeFromString<List<SiteData>>(sharedPreferencesManager.siteList)
        }
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun savePassword(activity: Activity, login: String, password: String, domain: String){
        viewModelScope.launch {

            saveCredential(
                activity = activity,
                login = login,
                password = password,
                domain = domain
            )

        }
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private suspend fun saveCredential(activity: Activity, login:String, password:String, domain: String){

        try {
            credentialManager.createCredential(
                request = CreatePasswordRequest(login, password),
                context = activity
            )
            Log.d("PASSWORD", "${getCredential(activity, "my@gmail.com")?.id}, ${getCredential(activity, "my@gmail.com")?.password}")
            saveNewDomain(domain, login)
        }catch (e:CreateCredentialException){
            e.printStackTrace()
            Log.d("PASSWORD", "error")
        }catch (e:CreateCredentialCancellationException){
            e.printStackTrace()
            Log.d("PASSWORD", "cancel")
        }


    }


    //@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    //fun getToDisplayPasswordCredential(activity: Activity){
//
    //    viewModelScope.launch {
//
    //        val passwordCredential = getCredential(activity) ?: return@launch
//
    //        Log.d("PASSWORD", "${passwordCredential.id}")
    //    }
//
//
    //}

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private suspend fun getCredential(activity: Activity, username:String):PasswordCredential?{

        try {
            val getCredentialRequest = GetCredentialRequest(
                listOf(GetPasswordOption(
                    allowedUserIds = setOf(username)
                ))
            )

            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = getCredentialRequest
            )

            return credentialResponse.credential as? PasswordCredential
        }catch (e:CreateCredentialException){
            e.printStackTrace()
            return null
        }

    }
}