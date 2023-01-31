package com.epmedu.animeal.feeding.domain.model

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING

data class Feeder(
    val id: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val time: String = EMPTY_STRING
)
