package com.epmedu.animeal.more.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.AnimealShortButton
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun ProfileScreenUI(
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(id = R.string.page_profile)) {
                BackButton(onClick = onBack)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(
                text = stringResource(R.string.page_profile_subtitle),
                modifier = Modifier.padding(top = 40.dp, start = 44.dp)
            )
            // TODO: Put profile fields
            AnimealShortButton(
                text = "Edit",
                onClick = onEdit,
                modifier = Modifier.padding(top = 32.dp, start = 24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ProfileScreenUIPreview() {
    AnimealTheme {
        ProfileScreenUI(
            onBack = {},
            onEdit = {},
        )
    }
}