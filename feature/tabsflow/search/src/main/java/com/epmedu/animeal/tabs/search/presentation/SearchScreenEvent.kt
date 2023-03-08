package com.epmedu.animeal.tabs.search.presentation

import com.epmedu.animeal.common.constants.DefaultConstants

internal sealed class SearchScreenEvent(open val id: String = DefaultConstants.EMPTY_STRING) {
    data class FeedSpotSelected(override val id: String) : SearchScreenEvent(id)

    data class FeedSpotChanged(
        override val id: String,
        val isFavorite: Boolean
    ) : SearchScreenEvent(id)

    object FeedingPointSheetHidden : SearchScreenEvent()

    data class ShowWillFeedDialog(override val id: String) : SearchScreenEvent(id)

    object DismissWillFeedDialog : SearchScreenEvent()
}