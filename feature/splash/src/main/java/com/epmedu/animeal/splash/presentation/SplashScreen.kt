package com.epmedu.animeal.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.common.screenRoute.MainScreenRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.spacer.HSpacer
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun SplashScreenPreview() {
    AnimealTheme {
        SplashScreenUI()
    }
}

@Composable
fun SplashScreen() {
    val navController = LocalNavigator.currentOrThrow
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // todo delete it after login logic implementation
            delay(2000)
            navController.navigate(MainScreenRoute.SignIn.name) {
                popUpTo(MainScreenRoute.Splash.name) {
                    inclusive = true
                }
            }
        }
    }

    SplashScreenUI()
}

@Composable
private fun SplashScreenUI(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.background,
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier.width(110.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(
                    id = R.drawable.ic_animeal_logo
                ),
                contentDescription = null
            )
            HSpacer(16.dp)
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                style = MaterialTheme.typography.h6,
            )
        }
    }
}
