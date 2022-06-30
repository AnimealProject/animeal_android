package com.epmedu.animeal.feature.more.profile

import android.util.Log
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    fun edit() {
        Log.i(TAG, "Edit clicked")
    }

    private companion object {
        private const val TAG = "ProfileViewModel"
    }
}