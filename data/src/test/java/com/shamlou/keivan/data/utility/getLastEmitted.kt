package com.shamlou.keivan.data.utility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

/**
 *
 * this function gets a stateflow and gets the last emitted item
 */
@ExperimentalCoroutinesApi
suspend fun <T> CoroutineScope.getLastEmitted(stateFlow: Flow<T>): T {

    val emitted = mutableListOf<T>()
    val job = launch {
        stateFlow.toList(emitted)
    }
    job.cancel()
    return emitted.last()
}
@ExperimentalCoroutinesApi
suspend fun <T> CoroutineScope.getListEmitted(stateFlow: Flow<T>): List<T> {

    val emitted = mutableListOf<T>()
    val job = launch {
        stateFlow.toList(emitted)
    }
    job.cancel()
    return emitted
}