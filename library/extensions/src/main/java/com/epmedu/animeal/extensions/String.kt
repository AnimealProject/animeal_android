package com.epmedu.animeal.extensions

fun String.containsAnyNotLetterCharacter() = any { it.isLetter().not() }