package com.example.internshipvk.data

import kotlinx.serialization.Serializable

@Serializable
data class SiteData(
    val domain:String,
    val login:String
)
