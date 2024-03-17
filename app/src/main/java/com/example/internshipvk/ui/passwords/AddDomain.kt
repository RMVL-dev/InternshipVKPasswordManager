package com.example.internshipvk.ui.passwords

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.example.internshipvk.R


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AddDomainScreen(
    modifier: Modifier = Modifier,
    viewModelPass: PasswordsViewModel,
    navigateToDomainList: () -> Unit
){

    var domain by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val activity = LocalContext.current as Activity

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = { Text(text = stringResource(id = R.string.domain).toUpperCase(Locale.current)) },
            value = domain,
            onValueChange = {domain = it},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = { Text(text = stringResource(id = R.string.login).toUpperCase(Locale.current)) },
            value = login,
            onValueChange = {login = it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = { Text(text = stringResource(id = R.string.password).toUpperCase(Locale.current)) },
            value = password,
            onValueChange = {password = it},
            visualTransformation = PasswordVisualTransformation(mask = '*'),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        )

        OutlinedButton(onClick = {
            viewModelPass.savePassword(
                activity,
                login = login,
                password = password,
                domain = domain
            )
            navigateToDomainList()
        }, modifier = modifier.fillMaxWidth()) {
            Box(
                modifier = modifier
            ){
                Text(text = stringResource(id = R.string.add_account).toUpperCase(Locale.current))
            }
        }
    }
}