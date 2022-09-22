package com.epmedu.animeal.home.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

class MarkerCache(private val context: Context) {

    private val maxSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    private val lruCache = LruCache<Int, Bitmap>(maxSize)

    fun getMarker(id: Int): Bitmap {
        return when (val cachedBitmap = lruCache.get(id)) {
            null -> {
                val bitmap = ContextCompat.getDrawable(context, id)?.toBitmap()
                lruCache.put(id, bitmap)
                lruCache.get(id)
            }
            else -> cachedBitmap
        }
    }
}