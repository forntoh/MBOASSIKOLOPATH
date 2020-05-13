package com.mboasikolopath.internal

import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

suspend fun <T> runOnUiThread(block: () -> T) {
    withContext(Dispatchers.Main) {
        block.invoke()
    }
}

suspend fun <T> runOnIoThread(block: suspend () -> T) = withContext(Dispatchers.IO) {
    return@withContext block.invoke()
}