package com.epmedu.animeal.foundation.markuptext

import java.util.regex.Pattern

internal object UnorderedListParser {

    private val UNORDERED_LIST_CONTENT_PATTERN = Pattern.compile("<ul>(.*?)</ul>")
    private const val LIST_ITEM_PREFIX = " • "

    fun parse(text: String): String {
        val result = StringBuffer()
        val matcher = UNORDERED_LIST_CONTENT_PATTERN.matcher(text)
        while (matcher.find()) {
            val newContent = matcher.group().replace("<li>", "<li>$LIST_ITEM_PREFIX")
            matcher.appendReplacement(result, newContent)
        }
        matcher.appendTail(result)
        return result.toString()
    }
}