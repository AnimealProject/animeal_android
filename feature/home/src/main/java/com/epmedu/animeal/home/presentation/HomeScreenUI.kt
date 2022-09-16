package com.epmedu.animeal.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.switch.AnimealSwitch
import com.epmedu.animeal.foundation.theme.bottomBarPadding
import com.epmedu.animeal.home.presentation.ui.CheckLocationPermission
import com.epmedu.animeal.home.presentation.ui.GeoLocationFloatingActionButton
import com.epmedu.animeal.home.presentation.ui.MapboxMap
import com.epmedu.animeal.home.presentation.viewmodel.HomeState

@Composable
internal fun HomeScreenUI(state: HomeState) {
    CheckLocationPermission {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .bottomBarPadding(),
            floatingActionButton = {
                GeoLocationFloatingActionButton(onClick = { /* TODO Implement onClick */ })
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MapboxMap(state)
                AnimealSwitch(
                    modifier = Modifier
                        .statusBarsPadding()
                        .align(alignment = Alignment.TopCenter)
                        .padding(top = 24.dp),
                    onSelectTab = {}
                )
            }
        }
    }
}
