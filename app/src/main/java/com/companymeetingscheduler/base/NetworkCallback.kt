package com.companymeetingscheduler.base

import com.companymeetingscheduler.constants.AppConstants
import com.companymeetingscheduler.data.model.FailureResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

abstract class NetworkCallback<T> : Callback<T> {

    abstract fun onSuccess(t: T?)

    abstract fun onFailure(failureResponse: FailureResponse)

    abstract fun onError(t: Throwable)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
            onFailure(FailureResponse.genericError())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is SocketTimeoutException || t is UnknownHostException) {
            val failureResponseForNoNetwork: FailureResponse = getFailureResponseForNoNetwork()
            onFailure(failureResponseForNoNetwork)
        } else if (t is ConnectException) {
            val failureResponseForUnableToConnect: FailureResponse =
                getFailureResponseForUnableToConnect()
            onFailure(failureResponseForUnableToConnect)
        } else {
            onError(t)
        }
    }

    private fun getFailureResponseForNoNetwork(): FailureResponse {
        return FailureResponse(
            AppConstants.NetworkingConstants.NO_INTERNET_CONNECTION,
            "No Internet Connection"
        )
    }

    private fun getFailureResponseForUnableToConnect(): FailureResponse {
        return FailureResponse(
            AppConstants.NetworkingConstants.INTERNAL_SERVER_ERROR_CODE,
            "Unable to connect to server"
        )
    }
}