package com.epmedu.animeal.foundation.text.parser

import java.util.regex.Pattern

internal object OrderedListParser {

    private val ORDERED_LIST_CONTENT_PATTERN = Pattern.compile("<ol>(.*?)</ol>")

    private const val LIST_ITEM_TAG = "<li>"

    fun parse(text: String): String {
        val result = StringBuffer()
        val matcher = ORDERED_LIST_CONTENT_PATTERN.matcher(text)
        while (matcher.find()) {
            var position = 1
            val newContent = matcher.group().replace(LIST_ITEM_TAG.toRegex()) {
                "$LIST_ITEM_TAG${getItemContentPrefix(position++)}"
            }
            matcher.appendReplacement(result, newContent)
        }
        matcher.appendTail(result)
        return result.toString()
    }

    private fun getItemContentPrefix(position: Int) = "<b> $position. </b>"
}