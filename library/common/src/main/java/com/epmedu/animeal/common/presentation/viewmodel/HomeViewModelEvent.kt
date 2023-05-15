package com.epmedu.animeal.common.presentation.viewmodel

sealed interface HomeViewModelEvent {
    object ShowCurrentFeedingPoint : HomeViewModelEvent
    object MinimiseBottomSheet : HomeViewModelEvent
}