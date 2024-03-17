package com.example.internshipvk.data

sealed interface Response {
    data class Success(val response:String):Response

    data object Error:Response

    data object Loading:Response

}