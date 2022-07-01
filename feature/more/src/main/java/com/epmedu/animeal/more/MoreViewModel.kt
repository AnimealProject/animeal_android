package com.epmedu.animeal.more

import android.util.Log
import androidx.lifecycle.ViewModel

internal class MoreViewModel : ViewModel() {

    internal fun logout() {
        Log.i(TAG, "Logout clicked")
    }

    private companion object {
        private const val TAG = "MoreViewModel"
    }
}