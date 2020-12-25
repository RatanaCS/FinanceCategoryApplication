package com.sumuzu.financecategoryapplication.model.getDataBalance

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetDataBalance(
    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean? = null
): Parcelable
