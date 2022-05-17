package com.example.pokemon.util

import java.lang.Exception

sealed class Result<out T> {
    object Loading : Result<Any>()
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Any>()
}