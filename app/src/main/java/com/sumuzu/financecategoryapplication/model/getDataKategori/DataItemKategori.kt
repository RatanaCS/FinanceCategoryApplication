package com.sumuzu.financecategoryapplication.model.getDataKategori

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemKategori(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("harga_beli")
    val harga_beli: String? = null,

    @field:SerializedName("harga_jual")
    val harga_jual: String? = null,

    @field:SerializedName("satuan")
    val satuan: String? = null,

    @field:SerializedName("id")
    val id: String? = null

): Parcelable