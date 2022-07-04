package com.epmedu.animeal.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.CustomColor
import com.epmedu.animeal.common.screenRoute.MainScreenRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.login.domain.model.OnBoardingItemModel
import com.epmedu.animeal.login.ui.LoginButtonContent
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.resources.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Suppress("UnusedPrivateMember")
@Composable
@Preview
private fun SignInScreenPreview() {
    AnimealTheme {
        SignInScreenUI(
            onSignInMobile = {},
            onSignInFacebook = {},
            onSignInGoogle = {},
        )
    }
}

@Composable
fun SignInScreen() {
    val navigator = LocalNavigator.currentOrThrow

    SignInScreenUI(
        onSignInMobile = {
            navigator.replaceSignIn(MainScreenRoute.Tabs)
        },
        onSignInFacebook = {
            navigator.replaceSignIn(MainScreenRoute.Tabs)
        },
        onSignInGoogle = {
            navigator.replaceSignIn(MainScreenRoute.Tabs)
        },
    )
}

@Composable
private fun SignInScreenUI(
    modifier: Modifier = Modifier,
    onSignInMobile: () -> Unit,
    onSignInFacebook: () -> Unit,
    onSignInGoogle: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            OnBoarding()
            ButtonsBlock(
                onSignInMobile = onSignInMobile,
                onSignInFacebook = onSignInFacebook,
                onSignInGoogle = onSignInGoogle,
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ColumnScope.OnBoarding(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 32.dp)
            .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val pagerState = rememberPagerState()
        val boardingItems = rememberOnBoardingItems()

        HorizontalPager(
            modifier = Modifier
                .weight(1f),
            state = pagerState,
            count = boardingItems.size,
        ) { page ->
            OnBoardingItem(
                model = boardingItems[page]
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            indicatorWidth = 12.dp,
            indicatorHeight = 6.dp,
            spacing = 8.dp,
            activeColor = MaterialTheme.colors.secondary,
            inactiveColor = MaterialTheme.colors.secondaryVariant,
        )
    }
}

@Composable
private fun OnBoardingItem(
    modifier: Modifier = Modifier,
    model: OnBoardingItemModel,
) {
    Column(
        modifier = modifier
            .padding(
                start = 54.dp,
                end = 54.dp,
                top = 16.dp,
                bottom = 24.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Image(
            modifier = Modifier.weight(1f),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = model.imageId),
            contentDescription = null
        )
        Text(
            text = model.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5,
        )
        Text(text = model.text)
    }
}

@Composable
private fun ButtonsBlock(
    modifier: Modifier = Modifier,
    onSignInMobile: () -> Unit,
    onSignInFacebook: () -> Unit,
    onSignInGoogle: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 20.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimealButton(
                onClick = onSignInMobile,
            ) {
                LoginButtonContent(
                    iconId = R.drawable.ic_phone,
                    textId = R.string.sign_in_mobile,
                )
            }
            AnimealButton(
                color = CustomColor.Facebook,
                onClick = onSignInFacebook,
            ) {
                LoginButtonContent(
                    iconId = R.drawable.ic_facebook,
                    textId = R.string.sign_in_facebook,
                )
            }
            AnimealButton(
                color = CustomColor.Google,
                onClick = onSignInGoogle,
            ) {
                LoginButtonContent(
                    iconId = R.drawable.ic_google,
                    textId = R.string.sign_in_google,
                )
            }
        }
    }
}

private fun Navigator.replaceSignIn(
    route: MainScreenRoute
) {
    navigate(route.name) {
        popUpTo(MainScreenRoute.SignIn.name) {
            inclusive = true
        }
    }
}

@Composable
private fun rememberOnBoardingItems(): List<OnBoardingItemModel> = remember {
    listOf(
        OnBoardingItemModel(
            imageId = R.drawable.ic_feed_us,
            title = LoremIpsum(3).values.joinToString(),
            text = LoremIpsum(5).values.joinToString(),
        ),
        OnBoardingItemModel(
            imageId = R.drawable.ic_feed_us,
            title = LoremIpsum(10).values.joinToString(),
            text = LoremIpsum(10).values.joinToString(),
        ),
        OnBoardingItemModel(
            imageId = R.drawable.ic_feed_us,
            title = LoremIpsum(3).values.joinToString(),
            text = LoremIpsum(20).values.joinToString(),
        )
    )
}
