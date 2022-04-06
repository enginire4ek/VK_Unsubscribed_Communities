package com.project.giniatovia.domain.common

sealed class Result {
    class Success<T>(val result: T)
    class Error(val exception: Exception)
}
