package com.sumuzu.financecategoryapplication.model.getDataIncome

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetDataIncome(
    @field:SerializedName("data")
    val data: List<DataItemIncome>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("subtot")
    val subtot: String? = null,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean? = null
): Parcelable
