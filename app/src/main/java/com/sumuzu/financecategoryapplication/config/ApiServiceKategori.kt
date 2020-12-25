package com.sumuzu.financecategoryapplication.config

import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataKategori.ResponseGetDataKategori
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceKategori {

    ////////////////Kategori
    //getData
    @GET("kategori/getDataKategori.php")
    fun getDataKategori() : Call<ResponseGetDataKategori>

    //getDatabyId
    @GET("kategori/getDataKategori.php?id=")
    fun getDataKategoriById(@Query("id") id : String) : Call<ResponseGetDataKategori>

    //insert
    @FormUrlEncoded
    @POST("kategori/insertKategori.php")
    fun insertData(@Field("kategori") kategori : String,
                   @Field("harga_beli") harga_beli : String,
                   @Field("harga_jual") harga_jual : String,
                   @Field("satuan") satuan : String
        ) : Call<ResponseAction>

    //update
    @FormUrlEncoded
    @POST("kategori/updateKategori.php")
    fun updateData(@Field("id") id : String,
                   @Field("kategori") kategori : String,
                   @Field("harga_beli") harga_beli : String,
                   @Field("harga_jual") harga_jual : String,
                   @Field("satuan") satuan : String
    ) : Call<ResponseAction>

    //delete
    @FormUrlEncoded
    @POST("kategori/deleteKategori.php")
    fun deleteData(@Field("id") id : String
    ) : Call<ResponseAction>

}