package com.example.internshipvk.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.internshipvk.InternshipApplication
import com.example.internshipvk.ui.passwords.PasswordsViewModel

class InternshipViewModelProvider {

    val factory = viewModelFactory {
        initializer {
            PasswordsViewModel(
                application = internshipApplication(),
                repository = internshipApplication().internshipContainer.repository
            )
        }
    }


}

fun CreationExtras.internshipApplication(): InternshipApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as InternshipApplication)