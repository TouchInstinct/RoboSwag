package ru.touchin.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.safetyLaunch(block: suspend () -> Unit) {
    launch {
        try {
            block.invoke()
        } catch (_: Throwable) { }
    }
}
