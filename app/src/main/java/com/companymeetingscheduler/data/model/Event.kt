package com.companymeetingscheduler.data.model

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

class Event<T>(private val content: T) {

    var isAlreadyHandled = false

    fun getContent(): T? {
        if (!isAlreadyHandled) {
            isAlreadyHandled = true
            return content
        }
        return null
    }

    fun peekContent(): T {
        return content
    }
}
