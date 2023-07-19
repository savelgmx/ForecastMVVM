package com.example.forecastmvvm.internal

import kotlinx.coroutines.*
//this class has to provide coroutine context in lazy block ,as it applied in CurrentViewModel foe example
fun <T> lazyDeffered(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }

    }
}