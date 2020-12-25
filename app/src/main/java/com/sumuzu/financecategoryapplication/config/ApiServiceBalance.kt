package com.sumuzu.financecategoryapplication.config

import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataBalance.ResponseGetDataBalance
import com.sumuzu.financecategoryapplication.model.getDataIncome.ResponseGetDataIncome
import com.sumuzu.financecategoryapplication.model.getDataKategori.ResponseGetDataKategori
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceBalance {

    ////////////////Balance
    //getData
    @GET("balance/getDataBalance.php")
    fun getDataBalance() : Call<ResponseGetDataBalance>

    //getDataByCategory
    @GET("balance/getDataBalance.php?kategori=")
    fun getDataBalanceByCategory(@Query("kategori") kategori : String) : Call<ResponseGetDataBalance>


}