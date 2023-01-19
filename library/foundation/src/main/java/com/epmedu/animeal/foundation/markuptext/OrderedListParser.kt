package com.epmedu.animeal.foundation.markuptext

import java.util.regex.Pattern

object OrderedListParser {

    private val ORDERED_LIST_CONTENT_PATTERN = Pattern.compile("<ol>(.*?)</ol>")

    fun parse(text: String): String {
        val result = StringBuffer()
        val matcher = ORDERED_LIST_CONTENT_PATTERN.matcher(text)
        while (matcher.find()) {
            var position = 1
            val newContent = matcher.group().replace("<li>".toRegex()) {
                "<li>${getItemPrefix(position++)}"
            }
            matcher.appendReplacement(result, newContent)
        }
        matcher.appendTail(result)
        return result.toString()
    }

    private fun getItemPrefix(position: Int) = "<b> $position. </b>"
}