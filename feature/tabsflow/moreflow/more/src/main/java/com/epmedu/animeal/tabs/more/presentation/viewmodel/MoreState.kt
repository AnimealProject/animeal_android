package com.epmedu.animeal.tabs.more.presentation.viewmodel

import com.epmedu.animeal.tabs.more.presentation.model.MoreOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MoreState(
    val options: ImmutableList<MoreOption> = persistentListOf()
)
