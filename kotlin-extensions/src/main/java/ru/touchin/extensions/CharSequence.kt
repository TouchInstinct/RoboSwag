package ru.touchin.extensions

fun CharSequence.indexesOf(substring: String) = Regex(substring).find(this)?.let { it.range.first to it.range.last + 1 }
