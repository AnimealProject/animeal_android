package com.epmedu.animeal.tabs.search.presentation.viewmodel

import com.epmedu.animeal.foundation.tabs.model.AnimalType

sealed interface SearchEvent {

    data class ShowHomePage(val feedingPointId: String, val animalType: AnimalType) : SearchEvent
}