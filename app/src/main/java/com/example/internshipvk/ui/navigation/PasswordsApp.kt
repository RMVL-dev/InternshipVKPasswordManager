package com.example.internshipvk.ui.navigation

import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.internshipvk.provider.InternshipViewModelProvider
import com.example.internshipvk.ui.passwords.AddDomainScreen
import com.example.internshipvk.ui.passwords.PasswordsViewModel
import com.example.internshipvk.ui.passwords.SiteListScreen
import java.util.concurrent.Executor

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    passwordsViewModel: PasswordsViewModel = viewModel(
        factory = InternshipViewModelProvider().factory
    )
){
    passwordsViewModel.context = LocalContext.current
    passwordsViewModel.initDomainList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = navController.currentDestination?.displayName ?: "Passwords Application"
                    )
                }
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = if (passwordsViewModel.domainList.isEmpty()) NavigationGraph.AddPassword.name else NavigationGraph.SiteList.name,
            modifier = modifier.padding(paddingValues = paddingValues)
        ){
            composable(route = NavigationGraph.SiteList.name){
                SiteListScreen(
                    passwordsViewModel = passwordsViewModel
                ){
                    navController.navigate(NavigationGraph.AddPassword.name)
                }
            }
            composable(route = NavigationGraph.AddPassword.name){
                AddDomainScreen(viewModelPass = passwordsViewModel){
                    navController.navigate(NavigationGraph.SiteList.name)
                }
            }

        }

    }

}
