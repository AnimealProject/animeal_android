package com.epmedu.animeal.extensions

/**
 * Replaces [oldElement] with [newElement] if is present in the collection
 * and returns the modified collection
 * @param oldElement Element to be replaced
 * @param newElement Element to replace
 */
fun <T : Any> List<T>.replaceElement(oldElement: T, newElement: T) = map {
    when (it) {
        oldElement -> newElement
        else -> it
    }
}