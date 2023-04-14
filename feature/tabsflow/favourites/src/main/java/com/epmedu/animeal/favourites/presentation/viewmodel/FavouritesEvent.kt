package com.epmedu.animeal.favourites.presentation.viewmodel

import com.epmedu.animeal.foundation.tabs.model.AnimalType

sealed interface FavouritesEvent {

    class ShowHomePage(val feedingPointId: String, val animalType: AnimalType) : FavouritesEvent
}