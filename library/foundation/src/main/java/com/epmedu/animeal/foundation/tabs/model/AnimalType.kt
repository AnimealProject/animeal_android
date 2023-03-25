package com.epmedu.animeal.foundation.tabs.model

import androidx.annotation.StringRes
import com.epmedu.animeal.resources.R

enum class AnimalType(@StringRes val title: Int) {
    Dogs(R.string.switch_dogs_title),
    Cats(R.string.switch_cats_title)
}