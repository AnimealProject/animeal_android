package com.epmedu.animeal.tabs.search.presentation.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.common.constants.Arguments
import com.epmedu.animeal.common.route.TabsRoute
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.ui.FeedingConfirmationDialog
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointActionButton
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState.FeedingStarted
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState.FeedingWasAlreadyBooked
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetLayout
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetState
import com.epmedu.animeal.foundation.bottomsheet.AnimealBottomSheetValue
import com.epmedu.animeal.foundation.bottomsheet.contentAlphaButtonAlpha
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.bottomBarHeight
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator
import com.epmedu.animeal.permissions.presentation.AnimealPermissions
import com.epmedu.animeal.permissions.presentation.PermissionStatus
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.ui.CameraPermissionRequestDialog
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent
import com.epmedu.animeal.tabs.search.presentation.viewmodel.SearchState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun SearchScreenUi(
    state: SearchState,
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (SearchScreenEvent) -> Unit,
    onFeedingEvent: (FeedingEvent) -> Unit,
    onPermissionsEvent: (PermissionsEvent) -> Unit
) {
    HandleFeedingPointSheetHiddenState(bottomSheetState, onEvent)

    val (contentAlpha: Float, buttonAlpha: Float) = bottomSheetState.contentAlphaButtonAlpha()

    val scope = rememberCoroutineScope()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch { bottomSheetState.hide() }
    }

    AnimealPermissions(
        permissionsState = state.permissionsState,
        lifecycleState = LocalLifecycleOwner.current.lifecycle.currentState,
        onEvent = onPermissionsEvent
    ) { _ ->
        ScreenScaffold(
            bottomSheetState,
            state,
            contentAlpha,
            buttonAlpha,
            scope,
            onEvent,
            onFeedingEvent,
        )
    }
}

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Suppress("LongMethod")
private fun ScreenScaffold(
    bottomSheetState: AnimealBottomSheetState,
    state: SearchState,
    contentAlpha: Float,
    buttonAlpha: Float,
    scope: CoroutineScope,
    onEvent: (SearchScreenEvent) -> Unit,
    onFeedingEvent: (FeedingEvent) -> Unit,
) {
    val isFeedingDialogShowing = rememberSaveable { mutableStateOf(false) }
    val isCameraPermissionDialogShowing = rememberSaveable { mutableStateOf(false) }

    val navigator = LocalNavigator.currentOrThrow
    AnimealBottomSheetLayout(
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 20.dp),
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        skipHalfExpanded = true,
        sheetContent = {
            SearchSheetContent(navigator, state, contentAlpha, onEvent)
        },
        sheetControls = {
            FeedingPointActionButton(
                alpha = buttonAlpha,
                enabled = state.showingFeedingPoint?.feedStatus == FeedStatus.RED,
                onClick = {
                    when (state.permissionsState.cameraPermissionStatus) {
                        PermissionStatus.Granted -> isFeedingDialogShowing.value = true
                        else -> isCameraPermissionDialogShowing.value = true
                    }
                },
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.secondaryVariant),
        ) { padding ->
            ScreenContent(state, scope, onEvent)
        }
    }

    CameraPermissionRequestDialog(isShowing = isCameraPermissionDialogShowing)
    state.showingFeedingPoint?.let {
        FeedingConfirmationDialog(
            isShowing = isFeedingDialogShowing,
            onAgreeClick = {
                scope.launch { bottomSheetState.hide() }
                onFeedingEvent(FeedingEvent.Start(it.id))
            }
        )
    }
    OnFeedingConfirmationStateChange(
        navigator,
        onFeedingEvent,
        state.feedState.feedingConfirmationState
    )
}

@Composable
fun OnFeedingConfirmationStateChange(
    navigator: Navigator,
    onFeedingEvent: (FeedingEvent) -> Unit,
    feedingConfirmationState: FeedingConfirmationState,
) {
    when (feedingConfirmationState) {
        FeedingStarted -> { navigator.navigateTo(TabsRoute.Home.name) }
        FeedingWasAlreadyBooked -> {
            AnimealAlertDialog(
                title = stringResource(id = R.string.feeding_point_expired_description),
                acceptText = stringResource(id = R.string.feeding_point_expired_accept),
                onConfirm = {
                    onFeedingEvent(FeedingEvent.Reset)
                }
            )
        }
        else -> {}
    }
}

@Composable
private fun SearchSheetContent(
    navigator: Navigator,
    state: SearchState,
    contentAlpha: Float,
    onEvent: (SearchScreenEvent) -> Unit
) {
    state.showingFeedingPoint?.let { feedingPoint ->
        FeedingPointSheetContent(
            feedingPoint = feedingPoint,
            contentAlpha = contentAlpha,
            modifier = Modifier.fillMaxHeight(),
            isShowOnMapVisible = true,
            onFavouriteChange = { isFavourite ->
                onEvent(SearchScreenEvent.FavouriteChange(isFavourite, feedingPoint))
            },
            onShowOnMap = {
                navigator.navigate(
                    TabsRoute.Home.withArg(
                        Arguments.FORCED_FEEDING_POINT_ID to feedingPoint.id,
                        Arguments.ANIMAL_TYPE to feedingPoint.animalType.name
                    )
                )
            }
        )
    }
}

@Composable
private fun HandleFeedingPointSheetHiddenState(
    bottomSheetState: AnimealBottomSheetState,
    onEvent: (SearchScreenEvent) -> Unit
) {
    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.isHidden }.distinctUntilChanged().filter { it }.collect {
            onEvent(SearchScreenEvent.FeedingPointHidden)
        }
    }
}

@Composable
private fun ScreenContent(
    state: SearchState,
    scope: CoroutineScope,
    onEvent: (SearchScreenEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {
        AnimalTypeHorizontalPager(
            tabRowHorizontalPadding = 30.dp,
            scope = scope
        ) { animalType ->

            SearchFeedingPointsUi(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = bottomBarHeight,
                        top = 12.dp
                    ),
                feedingPoints = state.getFeedingPointsBy(animalType),
                animalType = animalType,
                onEvent = onEvent,
                query = state.getQueryBy(animalType),
            )
        }
    }
}

@AnimealPreview
@Composable
private fun SearchScreenUiPreview() {
    AnimealTheme {
        SearchScreenUi(
            SearchState(),
            AnimealBottomSheetState(AnimealBottomSheetValue.Hidden),
            {},
            {},
            {},
        )
    }
}