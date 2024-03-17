package com.example.internshipvk

import android.app.Application
import com.example.internshipvk.di.implementations.InternshipContainerImpl
import com.example.internshipvk.di.interfaces.InternshipContainer

class InternshipApplication:Application() {

    lateinit var internshipContainer: InternshipContainer

    override fun onCreate() {
        super.onCreate()
        internshipContainer = InternshipContainerImpl()
    }

}