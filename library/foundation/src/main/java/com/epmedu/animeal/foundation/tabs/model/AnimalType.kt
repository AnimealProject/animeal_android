package com.epmedu.animeal.foundation.tabs.model

import androidx.annotation.StringRes
import com.epmedu.animeal.resources.R

enum class AnimalType(@StringRes val title: Int) {
    Dogs(R.string.switch_dogs_title),
    Cats(R.string.switch_cats_title);

    // todo it possible to use the enum 'name' instead if the obfuscation will exclude that enum
    fun getName(): String =
        when (this) {
            Dogs -> DOGS
            Cats -> CATS
        }

    /**
     * todo
     * remove it consts and use the enum value,
     * if the obfuscation will exclude the AnimalType enum
     */
    companion object {
        private const val DOGS = "dogs"
        private const val CATS = "cats"
        fun getByName(name: String): AnimalType = if (name == CATS) Cats else Dogs
    }
}