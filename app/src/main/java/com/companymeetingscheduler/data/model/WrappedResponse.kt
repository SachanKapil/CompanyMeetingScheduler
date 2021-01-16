package com.companymeetingscheduler.data.model

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

data class WrappedResponse<T>(
    var data: T? = null,
    var failureResponse: FailureResponse? = null
)