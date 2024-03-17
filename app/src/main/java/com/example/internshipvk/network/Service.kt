package com.example.internshipvk.network

import retrofit2.http.GET
import retrofit2.http.Url

interface Service {
    @GET
    suspend fun getFavIcon(@Url url:String):String
}