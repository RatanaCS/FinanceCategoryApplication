package com.sumuzu.financecategoryapplication.model.getDataKategori

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGetDataKategori(
    @field:SerializedName("data")
    val data: List<DataItemKategori>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean? = null
): Parcelable
