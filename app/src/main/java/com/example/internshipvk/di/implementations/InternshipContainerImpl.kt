package com.example.internshipvk.di.implementations

import com.example.internshipvk.di.interfaces.InternshipContainer
import com.example.internshipvk.di.interfaces.InternshipRepository
import com.example.internshipvk.network.Service
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class InternshipContainerImpl:InternshipContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/search/")
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("json/application".toMediaType()))
        .build()

    private val retrofitService: Service by lazy {
        retrofit.create(Service::class.java)
    }


    override val repository: InternshipRepository by lazy {
        InternshipRepositoryImpl(retrofitService)
    }
}