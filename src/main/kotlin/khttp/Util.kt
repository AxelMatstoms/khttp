package khttp

import java.util.*
import java.util.regex.Matcher

fun <K, V> Map<K, V>.joinToString(prefix: String = "{",
                                  kvSeparator: String = ": ",
                                  separator: String = ", ",
                                  postfix: String = "}",
                                  keyTransform: (K) -> String = {
                                      it.toString()
                                  }, valueTransform: (V) -> String = {
            it.toString()
        }): String {
    return this.entries.joinToString(prefix = prefix,
            separator = separator,
            postfix = postfix,
            transform = { (key, value) ->
                keyTransform(key) + kvSeparator + valueTransform(value)
            })
}

fun <K, V> String.toMap(separator: String = ", ",
                        prefix: String = "{",
                        postfix: String = "}",
                        keyPrefix: String? = "\"",
                        keyPostfix: String? = "\"",
                        valuePrefix: String? = "\"",
                        valuePostfix: String? = "\"",
                        keyTranform: (String) -> K = {
                            it as K
                        },
                        valueTransform: (String) -> V = {
                            it as V
                        }) {
    var s: String = substring(prefix.length .. (length - postfix.length - 1))
    println(s)
}

fun Matcher.maybeGroup(group: String): String? {
    return try {
        this.group(group)
    } catch (iae: IllegalArgumentException) {
        null
    }
}

fun String.uriEncode(): String {
    val sb = StringBuilder(length * 2)
    for (char in toCharArray()) {
        var shouldEncode = !(char.isLetter() || char.isDigit() || char == '.' || char == '_' || char == '-' || char == '~')
        if (shouldEncode) {
            sb.append(String.format(Locale.US, "%%%02X", char.toInt()))
        } else {
            sb.append(char.toString())
        }
    }
    return sb.toString()
}