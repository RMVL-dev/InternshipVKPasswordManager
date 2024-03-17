package com.example.internshipvk

import android.app.Application
import com.example.internshipvk.di.implementations.InternshipContainerImpl
import com.example.internshipvk.di.interfaces.InternshipContainer

class InternshipApplication:Application() {

    //Ввел di чтобы следовать чистой архитектуре, но почитал, что hilt будет излишним)

    lateinit var internshipContainer: InternshipContainer

    override fun onCreate() {
        super.onCreate()
        internshipContainer = InternshipContainerImpl(application = this)
    }

}