package com.shamlou.keivan.domain.util

import com.shamlou.keivan.domain.model.ErrorModel


data class Resource<out T>(val status: Status, val data: T?, val error: ErrorModel?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: ErrorModel?, data: T?=null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T?=null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    fun isSuccess() = status == Status.SUCCESS
    fun isLoading() = status == Status.LOADING
    fun isError() = status == Status.ERROR
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}