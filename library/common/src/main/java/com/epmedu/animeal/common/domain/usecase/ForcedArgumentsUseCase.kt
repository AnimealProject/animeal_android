package com.epmedu.animeal.common.domain.usecase

import androidx.lifecycle.SavedStateHandle

class ForcedArgumentsUseCase(private val savedStateHandle: SavedStateHandle) {

    private val alreadyAnswered = mutableSetOf<Int>()

    operator fun <T> invoke(name: String, hash: Int): T? {
        val result = if (alreadyAnswered.contains(hash)) null else savedStateHandle.get<T>(name)
        alreadyAnswered.add(hash)

        return result
    }
}