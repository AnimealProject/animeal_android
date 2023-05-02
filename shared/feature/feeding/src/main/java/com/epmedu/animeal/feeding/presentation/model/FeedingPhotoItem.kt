package com.epmedu.animeal.feeding.presentation.model

import android.net.Uri
import androidx.core.net.toUri

data class FeedingPhotoItem(val uri: Uri, val name: String) {
    companion object {
        val empty = FeedingPhotoItem("http://example.com".toUri(), "example.jpg")
    }
}