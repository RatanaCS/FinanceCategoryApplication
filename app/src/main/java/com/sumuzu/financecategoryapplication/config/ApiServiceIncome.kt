package com.sumuzu.financecategoryapplication.config

import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataIncome.ResponseGetDataIncome
import com.sumuzu.financecategoryapplication.model.getDataKategori.ResponseGetDataKategori
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceIncome {

    ////////////////Income
    //getData
    @GET("income/getDataIncome.php")
    fun getDataIncome() : Call<ResponseGetDataIncome>

    //getDataByCategory
    @GET("income/getDataIncome.php?kategori=")
    fun getDataIncomeByCategory(@Query("kategori") kategori : String) : Call<ResponseGetDataIncome>

    //insert
    @FormUrlEncoded
    @POST("income/insertIncome.php")
    fun insertData(@Field("kategori") kategori : String,
                   @Field("jumlah") jumlah : String,
                   @Field("tgl_transaksi") tgl_transaksi : String
        ) : Call<ResponseAction>

    //update
    @FormUrlEncoded
    @POST("income/updateIncome.php")
    fun updateData(@Field("id") id : String,
                   @Field("kategori") kategori : String,
                   @Field("jumlah") jumlah : String,
                   @Field("tgl_transaksi") tgl_transaksi : String
    ) : Call<ResponseAction>

    //delete
    @FormUrlEncoded
    @POST("income/deleteIncome.php")
    fun deleteData(@Field("id") id : String
    ) : Call<ResponseAction>

}