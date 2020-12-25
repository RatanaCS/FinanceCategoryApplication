package com.sumuzu.financecategoryapplication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseAction(
    val message: String? = null,
    val isSuccess: Boolean? = null
) : Parcelable