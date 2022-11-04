package com.epmedu.animeal.home.presentation.viewmodel

internal sealed interface HomeViewModelEvent {
    object ShowCurrentFeedingPoint : HomeViewModelEvent
    object StartRouteFlow : HomeViewModelEvent
}