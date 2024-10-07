package com.alkindi.gcg_akor.data

sealed class Result<out R> private constructor() {
    data class SUCCESS<out T>(val data: T) : Result<T>()
    data class ERROR(val error: String) : Result<Nothing>()
   object Loading : Result<Nothing>()
}