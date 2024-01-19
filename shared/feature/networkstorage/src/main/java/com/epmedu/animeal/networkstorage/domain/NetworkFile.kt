package com.epmedu.animeal.networkstorage.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NetworkFile(
    val name: String,
    val url: String
) : Parcelable
