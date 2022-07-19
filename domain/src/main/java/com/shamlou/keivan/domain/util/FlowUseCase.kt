package com.shamlou.keivan.domain.util

import com.shamlou.keivan.domain.model.ErrorModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface FlowUseCase<P, R> {
    fun invoke(parameters: P): Flow<Resource<R>> {
        try {
            return execute(parameters)
                .catch { e -> emit(Resource.error(ErrorModel(e.message?:""))) }
                .flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            return flow { emit(Resource.error(ErrorModel(e.message?:""))) }
        }
    }

    fun execute(parameters: P): Flow<Resource<R>>
}
