package com.companymeetingscheduler.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by {Kapil Sachan} on 18/11/2020.
 */

data class BaseResponse<T>(

    @SerializedName("message")
    @Expose
    var message: String? = null,
    @SerializedName("error_code")
    @Expose
    val errorCode: Int? = null,
    @SerializedName("data")
    @Expose
    val data: T? = null
)