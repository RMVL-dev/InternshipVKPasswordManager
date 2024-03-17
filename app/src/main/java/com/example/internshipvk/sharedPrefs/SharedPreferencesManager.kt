package com.example.internshipvk.sharedPrefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesManager(
    context: Context
) {

    companion object{
        const val FILE_NAME = "SiteList"
        const val LIST_NAME = "pref_sites"
    }

    private val pref: SharedPreferences

    init {
        pref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    var siteList:String
        get() = pref.getString(LIST_NAME, "") ?: ""
        set(value) = pref.edit{ putString(LIST_NAME, value) }
}