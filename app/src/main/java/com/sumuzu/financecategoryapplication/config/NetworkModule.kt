package com.sumuzu.financecategoryapplication.config

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    fun getRetrofit() : Retrofit {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        //Tetring HP
        return Retrofit.Builder().baseUrl("http://192.168.43.150/financecategory/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

//        //Wifi Kantor
//        return Retrofit.Builder().baseUrl("http://192.168.1.38/financecategory/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()

//        //Wifi Kantor Smd
//        return Retrofit.Builder().baseUrl("http://192.168.100.51/financecategory/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()

    }

    fun serviceKategori() : ApiServiceKategori = getRetrofit().create(ApiServiceKategori::class.java)
    fun serviceIncome() : ApiServiceIncome = getRetrofit().create(ApiServiceIncome::class.java)
    fun serviceExpenses() : ApiServiceExpenses = getRetrofit().create(ApiServiceExpenses::class.java)
    fun serviceBalance() : ApiServiceBalance = getRetrofit().create(ApiServiceBalance::class.java)


}
