package com.example.internshipvk.data

import kotlinx.serialization.Serializable

@Serializable
data class DomainData(
    val domain:String,
    val login:String
)
