package com.sumuzu.financecategoryapplication.model.getDataExpenses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetDataExpenses(
    @field:SerializedName("data")
    val data: List<DataItemExpenses>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("subtot")
    val subtot: String? = null,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean? = null
): Parcelable
