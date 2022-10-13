package com.epmedu.animeal.extensions

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}