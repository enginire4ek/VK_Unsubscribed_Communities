package com.project.giniatovia.domain.interfaces

import com.project.giniatovia.domain.common.Result

interface ResultCallback<T> {
    fun onResult(result: Result.Success<T>)
    fun onError(result: Result.Error)
}
