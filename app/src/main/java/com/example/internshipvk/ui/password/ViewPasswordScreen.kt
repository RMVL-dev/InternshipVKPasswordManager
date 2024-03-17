package com.example.internshipvk.ui.password

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.example.internshipvk.ui.passwords.PasswordsViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ViewPassword(
    modifier: Modifier = Modifier,
    viewModel: PasswordsViewModel,
    navigateToEdit: () -> Unit
){
    val activity = LocalContext.current as Activity
    viewModel.getToDisplayPasswordCredential(activity = activity)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            modifier = modifier.fillMaxWidth(),
            text = viewModel.currentDomain?.domain ?: "domain not found",
            textAlign = TextAlign.Center
        )

        Text(
            modifier = modifier.fillMaxWidth(),
            text = viewModel.currentDomain?.login ?: "login not found",
            textAlign = TextAlign.Center
        )

        Text(
            modifier = modifier.fillMaxWidth(),
            text = viewModel.currentPassword ?: "password not found",
            textAlign = TextAlign.Center
        )

        Button(
            modifier = modifier.fillMaxWidth(),
            onClick = {
                navigateToEdit()
            }
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Edit".toUpperCase(Locale.current),
                textAlign = TextAlign.Center
            )
        }
    }

}