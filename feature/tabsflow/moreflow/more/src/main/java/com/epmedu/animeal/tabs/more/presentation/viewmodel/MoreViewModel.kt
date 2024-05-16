package com.epmedu.animeal.tabs.more.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import com.epmedu.animeal.feedings.presentation.viewmodel.handlers.FeedingsButtonHandler
import com.epmedu.animeal.tabs.more.presentation.model.MoreOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MoreViewModel @Inject constructor(
    private val feedingsButtonHandler: FeedingsButtonHandler
) : ViewModel(), StateDelegate<MoreState> by DefaultStateDelegate(MoreState()) {

    init {
        viewModelScope.launch {
            val options = mutableSetOf(
                MoreOption.Profile,
                MoreOption.Feedings(),
                MoreOption.FAQ,
                MoreOption.About,
                MoreOption.Account,
                MoreOption.Donate,
            )

            feedingsButtonHandler.getFeedingsButtonState(
                shouldFetchFeedings = false
            ).collect { buttonState ->
                when (buttonState) {
                    FeedingsButtonState.Hidden -> {
                        options.removeIf { it is MoreOption.Feedings }
                    }
                    else -> {
                        options.removeIf { it is MoreOption.Feedings }
                        options.add(
                            MoreOption.Feedings(
                                isIndicatorEnabled = buttonState == FeedingsButtonState.Pulsating
                            )
                        )
                    }
                }
                updateState {
                    copy(options = options.sortedBy { it.route.ordinal }.toImmutableList())
                }
            }
        }
    }
}