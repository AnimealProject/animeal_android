package com.epmedu.animeal.more.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.ui.AnimealShortButton
import com.epmedu.animeal.base.ui.BackButton
import com.epmedu.animeal.base.ui.TopBar
import com.epmedu.animeal.more.R

@Composable
internal fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(id = R.string.profile)) {
                BackButton(onClick = { navController.popBackStack() })
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(
                text = stringResource(R.string.profile_subtitle),
                modifier = Modifier.padding(top = 40.dp, start = 44.dp)
            )
            // TODO: Put profile fields
            AnimealShortButton(
                text = "Edit",
                onClick = { viewModel.edit() },
                modifier = Modifier.padding(top = 32.dp, start = 24.dp)
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    AnimealTheme {
        ProfileScreen(navController = NavController(LocalContext.current))
    }
}