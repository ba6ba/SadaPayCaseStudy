package com.ba6ba.sadapaycasestudy

import com.ba6ba.sadapaycasestudy.managers.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface FlowUseCase<in P, R> {
    operator fun invoke(parameters: P): Flow<R> =
        execute(parameters).flowOn(dispatcher())

    fun execute(parameters: P): Flow<R>
    fun dispatcher(): CoroutineDispatcher
}
