package com.epmedu.animeal.api.feeding

import com.epmedu.animeal.extensions.YEAR_MONTH_DAY_DASH_FORMATTER
import type.SearchableStringFilterInput
import java.time.LocalDate

object FeedingFilters {
    private const val FEEDINGS_LIMIT_IN_DAYS = 10L

    val feedingsCreatedAtFilterInput: SearchableStringFilterInput =
        SearchableStringFilterInput.builder()
            .gte(
                LocalDate.now().minusDays(FEEDINGS_LIMIT_IN_DAYS)
                    .format(YEAR_MONTH_DAY_DASH_FORMATTER)
            )
            .build()
}