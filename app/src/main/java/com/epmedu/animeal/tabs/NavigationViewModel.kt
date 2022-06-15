package com.epmedu.animeal.tabs

import android.util.Log
import androidx.lifecycle.ViewModel

internal class NavigationViewModel : ViewModel() {

    fun onDestinationChanged(newDestination: TabActivity.NavigationScreen.Route) {
        Log.i(TAG, "onDestinationChanged: New destination: ${newDestination.name}")
    }

    private companion object {
        private const val TAG = "NavigationViewModel"
    }
}