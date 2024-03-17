package com.example.internshipvk.ui.passwords

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.internshipvk.R
import com.example.internshipvk.data.DomainData


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SiteListScreen(
    modifier: Modifier = Modifier,
    passwordsViewModel: PasswordsViewModel,
    navigateToAddDomain: () -> Unit,
    navigateToViewPassword: () -> Unit
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
                DomainCard(
                    domainItem = domain,
                    onClick = {
                        passwordsViewModel.currentDomain = it
                        navigateToViewPassword()
                    }
                )
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomainCard(
    modifier: Modifier = Modifier,
    domainItem: DomainData,
    onClick:(DomainData) -> Unit
){

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 8.dp),
        onClick = {
            onClick(domainItem)
        }
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
