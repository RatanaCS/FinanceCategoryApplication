package com.sumuzu.financecategoryapplication.model.getDataExpenses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemExpenses(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("jumlah")
    val jumlah: Int? = null,

    @field:SerializedName("nominal")
    val nominal: Int? = null,

    @field:SerializedName("tgl_transaksi")
    val tgl_transaksi: String? = null,

    @field:SerializedName("subtot")
    val subtot: Int? = null,

    @field:SerializedName("id")
    val id: String? = null

): Parcelable