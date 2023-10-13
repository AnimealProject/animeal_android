package com.epmedu.animeal.feedings.presentation.viewmodel

import com.epmedu.animeal.feedings.presentation.model.FeedingItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FeedingsState(
    val feedings: ImmutableList<FeedingItem> = persistentListOf()
)