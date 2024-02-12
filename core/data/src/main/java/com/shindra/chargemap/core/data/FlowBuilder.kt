package com.shindra.chargemap.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> flowAndEmit( block : suspend () -> T) : Flow<T> = flow {
    emit(block())
}