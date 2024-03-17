package com.example.internshipvk.ui.passwords

import android.hardware.biometrics.BiometricManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.internshipvk.R
import com.example.internshipvk.data.SiteData


@Composable
fun SiteListScreen(
    modifier: Modifier = Modifier,
    passwordsViewModel: PasswordsViewModel,
    navigateToAddDomain: () -> Unit
){

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (domainRecycler, addNewDomainButton) = createRefs()
        LazyColumn(
            modifier = modifier
                .constrainAs(domainRecycler){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ){
            items(passwordsViewModel.domainList){domain ->
                DomainCard(domainItem = domain)
            }
        }
        OutlinedButton(
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(addNewDomainButton) {
                    bottom.linkTo(parent.bottom)
                },
            onClick = {
                navigateToAddDomain()
            }
        ) {
            Text(text = stringResource(id = R.string.add_account))
        }
    }
}


@Composable
fun DomainCard(
    modifier: Modifier = Modifier,
    domainItem: SiteData
){

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 8.dp)
        ){
        Row {
            Image(painter = painterResource(
                id = R.drawable.ic_launcher_foreground),
                contentDescription = "domain image"
            )
            Column {
                Text(text = domainItem.domain)
                Text(text = domainItem.login)
            }
        }
    }

}

@Preview
@Composable
fun PreviewCard(){
    DomainCard(domainItem = SiteData("Google.com", "MyMail@gmail.com"))
}