package com.epmedu.animeal.feature.more.more

import android.util.Log
import androidx.lifecycle.ViewModel

internal class MainViewModel : ViewModel() {

    internal fun logout() {
        Log.i(TAG, "Logout clicked")
    }

    private companion object {
        private const val TAG = "MoreViewModel"
    }
}