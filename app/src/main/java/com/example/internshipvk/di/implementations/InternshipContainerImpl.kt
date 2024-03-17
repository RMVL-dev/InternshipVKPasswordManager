package com.example.internshipvk.di.implementations

import android.app.Application
import com.example.internshipvk.di.interfaces.InternshipContainer
import com.example.internshipvk.di.interfaces.InternshipRepository
import com.example.internshipvk.network.Service
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class InternshipContainerImpl(
    private val application: Application
):InternshipContainer {
    //Использую scalars Converter для получения разметки html
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.ge.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private val retrofitService: Service by lazy {
        retrofit.create(Service::class.java)
    }

    override val repository: InternshipRepository by lazy {
        InternshipRepositoryImpl(
            retrofitService,
            application = application
        )
    }
}