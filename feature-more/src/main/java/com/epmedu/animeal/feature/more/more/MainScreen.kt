package com.epmedu.animeal.feature.more.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.ui.ShortButton
import com.epmedu.animeal.base.ui.TopBar
import com.epmedu.animeal.feature.more.navigation.screens
import com.epmedu.animeal.feature_more.R

@Composable
internal fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(title = stringResource(id = R.string.more)) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            LazyColumn {
                items(screens) { screen ->
                    MoreOption(
                        title = stringResource(id = screen.title),
                        onClick = { navController.navigate(screen.route.name) }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            ShortButton(
                text = stringResource(id = R.string.logout),
                onClick = { viewModel.logout() },
                modifier = Modifier.padding(start = 44.dp, bottom = 44.dp)
            )
        }
    }
}

@Composable
private fun MoreOption(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { onClick() }
            .padding(start = 44.dp, end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title)
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = title,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview
@Composable
fun MoreOptionPreview() {
    AnimealTheme {
        Surface {
            MoreOption("Profile Page") {}
        }
    }
}

@Preview
@Composable
fun MoreScreenPreview() {
    AnimealTheme {
        MainScreen(navController = NavController(LocalContext.current))
    }
}
