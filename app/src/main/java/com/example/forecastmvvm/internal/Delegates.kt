package com.example.forecastmvvm.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
//this class has to provide coroutine context in lazy block ,as it applied in CurrentViewModel foe example



fun <T> lazyDeffered(
    scope: CoroutineScope,
    block: suspend CoroutineScope.() -> T
): Lazy<LiveData<T>> {
    return lazy {
        val liveData = MediatorLiveData<T>()
        scope.launch(Dispatchers.IO) {
            val result = withContext(Dispatchers.Main) {
                block.invoke(this)
            }
            liveData.postValue(result)
        }
        liveData
    }
}
