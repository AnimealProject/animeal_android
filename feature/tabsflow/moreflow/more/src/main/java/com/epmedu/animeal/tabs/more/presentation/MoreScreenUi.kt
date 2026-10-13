package com.epmedu.animeal.tabs.more.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.extensions.testTagAsResourceId
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.guest.GuestSession
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.presentation.MoreScreenEvent.Delete
import com.epmedu.animeal.tabs.more.presentation.MoreScreenEvent.Logout
import com.epmedu.animeal.tabs.more.presentation.model.MoreOption
import com.epmedu.animeal.tabs.more.presentation.ui.DeleteAccountConfirmationDialog
import com.epmedu.animeal.tabs.more.presentation.ui.FeedingsIndicator
import com.epmedu.animeal.tabs.more.presentation.ui.LogoutConfirmationDialog
import com.epmedu.animeal.tabs.more.presentation.ui.MoreOption
import com.epmedu.animeal.tabs.more.presentation.viewmodel.MoreSection
import com.epmedu.animeal.tabs.more.presentation.viewmodel.MoreState
import kotlinx.collections.immutable.persistentListOf

@Suppress("LongMethod")
@Composable
internal fun MoreScreenUi(
    state: MoreState,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onDisablingRouteForGuest: () -> Unit,
    onEvent: (MoreScreenEvent) -> Unit,
) {
    val isLogoutConfirmationDialogShowing = rememberSaveable { mutableStateOf(false) }
    val isDeleteAccountConfirmationDialogShowing = rememberSaveable { mutableStateOf(false) }

    LogoutConfirmationDialog(
        isShowing = isLogoutConfirmationDialogShowing,
        onConfirm = { onEvent(Logout) }
    )

    DeleteAccountConfirmationDialog(
        isShowing = isDeleteAccountConfirmationDialogShowing,
        onConfirm = { onEvent(Delete) }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .bottomBarPadding(),
        topBar = {
            TopBar(
                title = stringResource(id = state.titleResource ?: R.string.more),
                modifier = Modifier.statusBarsPadding()
            ) {
                state.titleResource?.let {
                    BackButton(onBack)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(vertical = 12.dp)
        ) {
            val sections = listOfNotNull(
                MoreSection(
                    title = EMPTY_STRING,
                    options = state.generalOptions
                ),
                state.moderationOptions
                    .takeIf { it.isNotEmpty() }
                    ?.let {
                        MoreSection(
                            title = stringResource(id = R.string.tab_section_admin),
                            options = it
                        )
                    }
            )

            LazyColumn {
                sections.forEachIndexed { _, section ->
                    if (section.title.isNotEmpty()) {
                        item {
                            Text(
                                section.title,
                                modifier = Modifier
                                    .padding(start = 24.dp, top = 36.dp, bottom = 8.dp),
                                style = MaterialTheme.typography.h6,
                            )
                        }
                    }
                    items(section.options) { option ->
                        when (option) {
                            MoreOption.Linebreak -> HeightSpacer(height = 24.dp)
                            else -> MoreOption(
                                modifier = Modifier
                                    .testTagAsResourceId("more_option_${option.route.name}"),
                                text = { MoreOptionItem(option) },
                                onClick = {
                                    when {
                                        GuestSession.isGuest.value && !option.route.allowGuest -> onDisablingRouteForGuest()
                                        option is MoreOption.DeleteAccount ->
                                            isDeleteAccountConfirmationDialogShowing.value = true
                                        else -> onNavigate(option.route.name)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (state.titleResource == null) {
                AnimealSecondaryButtonOutlined(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .testTagAsResourceId("btn_logout"),
                    text = stringResource(id = R.string.logout),
                    onClick = { isLogoutConfirmationDialogShowing.value = true },
                )
            }
        }
    }
}

@Composable
internal fun MoreOptionItem(option: MoreOption) {
    val isDeleteAccount = option is MoreOption.DeleteAccount
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Box(modifier = Modifier.width(24.dp)) {
            option.iconResource?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = if (isDeleteAccount) CustomColor.CarminePink else LocalContentColor.current
                )
            }
        }
        Text(
            text = stringResource(id = option.stringResource),
            color = if (isDeleteAccount) CustomColor.CarminePink else Color.Unspecified
        )

        if (option is MoreOption.Feedings && option.isIndicatorEnabled) {
            FeedingsIndicator()
        }
    }
}

@AnimealPreview
@Composable
private fun MoreScreenPreview() {
    AnimealTheme {
        MoreScreenUi(
            state = MoreState(
                generalOptions = persistentListOf(
                    MoreOption.Account,
                ),
                moderationOptions = persistentListOf(
                    MoreOption.Feedings(),
                    MoreOption.Linebreak,
                    MoreOption.Feedings(isIndicatorEnabled = true)
                )
            ),
            onBack = {},
            onDisablingRouteForGuest = {},
            onNavigate = {},
            onEvent = {},
        )
    }
}
