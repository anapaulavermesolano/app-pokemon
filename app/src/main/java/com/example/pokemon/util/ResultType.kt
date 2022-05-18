package com.example.pokemon.util

import okhttp3.ResponseBody

sealed class ResultType<out T> {
    data class Success<out T>(val value: T) : ResultType<T>()
    data class Error(val isNetworkError: Boolean, val errorBody: ResponseBody?) : ResultType<Nothing>()
    object Loading : ResultType<Nothing>()
}