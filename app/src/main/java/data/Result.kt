package data

import kotlin.Result

sealed class OutputResult<out T> {
    data class Success<out T>(val data: T) : OutputResult<T>()
    data class Error(val exception: Exception) : OutputResult<Nothing>()
}