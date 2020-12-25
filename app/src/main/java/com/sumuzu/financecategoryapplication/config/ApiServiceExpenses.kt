package com.sumuzu.financecategoryapplication.config

import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataExpenses.ResponseGetDataExpenses
import com.sumuzu.financecategoryapplication.model.getDataIncome.ResponseGetDataIncome
import com.sumuzu.financecategoryapplication.model.getDataKategori.ResponseGetDataKategori
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceExpenses {

    ////////////////Expenses
    //getData
    @GET("expenses/getDataExpenses.php")
    fun getDataExpenses() : Call<ResponseGetDataExpenses>

    //getDataByCategory
    @GET("expenses/getDataExpenses.php?kategori=")
    fun getDataExpensesByCategory(@Query("kategori") kategori : String) : Call<ResponseGetDataExpenses>

    //insert
    @FormUrlEncoded
    @POST("expenses/insertExpenses.php")
    fun insertData(@Field("kategori") kategori : String,
                   @Field("jumlah") jumlah : String,
                   @Field("tgl_transaksi") tgl_transaksi : String
        ) : Call<ResponseAction>

    //update
    @FormUrlEncoded
    @POST("expenses/updateExpenses.php")
    fun updateData(@Field("id") id : String,
                   @Field("kategori") kategori : String,
                   @Field("jumlah") jumlah : String,
                   @Field("tgl_transaksi") tgl_transaksi : String
    ) : Call<ResponseAction>

    //delete
    @FormUrlEncoded
    @POST("expenses/deleteExpenses.php")
    fun deleteData(@Field("id") id : String
    ) : Call<ResponseAction>

}