package com.example.forecastmvvm.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
//this class has to provide coroutine context in lazy block ,as it applied in CurrentViewModel foe example
fun <T> lazyDeffered(
    scope: CoroutineScope,
    block: suspend CoroutineScope.() -> T
): Lazy<LiveData<T>> {
    return lazy {
        MutableLiveData<T>().apply {
            scope.launch(Dispatchers.IO) {
                value = block.invoke(this)
            }
        }

    }
}