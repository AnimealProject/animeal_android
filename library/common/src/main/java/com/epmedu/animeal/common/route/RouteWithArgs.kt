package com.epmedu.animeal.common.route

interface RouteWithArgs {
    val name: String

    fun withArg(vararg args: Pair<String, String>): String =
        buildString {
            append(name)
            args.forEach { (arg, value) ->
                append("?$arg=$value")
            }
        }

    fun format(vararg args: String): String =
        buildString {
            append(name)
            args.forEach {
                append("?$it={$it}")
            }
        }
}