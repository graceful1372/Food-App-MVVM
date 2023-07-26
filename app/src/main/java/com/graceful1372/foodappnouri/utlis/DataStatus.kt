package com.graceful1372.foodappnouri.utlis

data class DataStatus<out T>(val status: Status, val data: T? = null, val isEmpty: Boolean) {

    enum class Status {
        SUCCESS
    }

    companion object {
        fun <T> success(data: T?, isEmpty: Boolean): DataStatus<T> {
            return DataStatus(Status.SUCCESS, data, isEmpty)
        }
    }
}