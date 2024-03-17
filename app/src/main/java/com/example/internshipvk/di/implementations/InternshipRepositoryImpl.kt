package com.example.internshipvk.di.implementations

import com.example.internshipvk.di.interfaces.InternshipRepository
import com.example.internshipvk.network.Service

class InternshipRepositoryImpl(
    private val service: Service
):InternshipRepository {
    override suspend fun getFavIcon(url: String) {
        service.getFavIcon(url)
    }
}