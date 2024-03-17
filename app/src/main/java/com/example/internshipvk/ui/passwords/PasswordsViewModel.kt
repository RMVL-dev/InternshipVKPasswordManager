package com.example.internshipvk.ui.passwords

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipvk.data.Response
import com.example.internshipvk.data.DomainData
import com.example.internshipvk.di.interfaces.InternshipRepository
import com.example.internshipvk.sharedPrefs.SharedPreferencesManager
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.Exception

class PasswordsViewModel(
    private val repository: InternshipRepository
): ViewModel() {

    var domainResponse:Response by mutableStateOf(Response.Loading)
    /*
    Да, скорее всего, это не безопасно так хранить, но идеи как передать
    пароль на экран просмотра у меня не нашлось :)
     */
    var currentPassword:String? by mutableStateOf("")
        private set

    var currentDomain:DomainData? by mutableStateOf(null)

    var domainList:List<DomainData> by mutableStateOf(emptyList())
        private set

    lateinit var context:Context

    //Инициализация списка доменов
    fun initDomainList(){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        domainList = try{
            Json.decodeFromString<List<DomainData>>(sharedPreferencesManager.siteList)
        }catch (e:Exception) {
            emptyList<DomainData>()
        }
    }


    //метод для получения разметки
    fun getRequest(url:String){
        viewModelScope.launch {
            domainResponse = try {
                Response.Success(
                    repository.getFavIcon(url = url)
                )
            }catch (e:Exception){
                e.printStackTrace()
                Response.Error
            }
        }
    }

    //сохранение домена и обновление списка для экрана со списком доменов
    private fun saveNewDomain(domain:String, login:String){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        if (sharedPreferencesManager.siteList.isNotEmpty()) {
            val previousData =
                Json.decodeFromString<List<DomainData>>(sharedPreferencesManager.siteList)
                    .toMutableList()
            val newData: MutableSet<DomainData> = previousData.toMutableSet()
            newData.add(DomainData(domain, login))
            sharedPreferencesManager.siteList = Json.encodeToString(newData)
            domainList = Json.decodeFromString<List<DomainData>>(sharedPreferencesManager.siteList)
        }else{
            sharedPreferencesManager.siteList = Json.encodeToString(listOf(DomainData(domain, login)))
            domainList = Json.decodeFromString<List<DomainData>>(sharedPreferencesManager.siteList)
        }
    }


    //метод сохранения входных данных
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun savePassword(activity: Activity, login: String, password: String, domain: String) {
        viewModelScope.launch {
            val success = repository.saveCredential(
                activity = activity,
                login = login,
                password = password,
            )
            if (success){
                saveNewDomain(
                    domain = domain,
                    login = login
                )
            }
        }
    }


    //получение пароля из хранилища на телефоне
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun getToDisplayPasswordCredential(activity: Activity){
        viewModelScope.launch {
            currentPassword =  try {
                val passwordCredential = repository.getCredential(
                    activity,
                    username = currentDomain?.login
                    ) ?: return@launch
                passwordCredential.password
            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        }
    }

}