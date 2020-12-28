package com.sumuzu.financecategoryapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.sumuzu.financecategoryapplication.category.KategoriActivity
import com.sumuzu.financecategoryapplication.config.NetworkModule
import com.sumuzu.financecategoryapplication.expenses.ExpensesActivity
import com.sumuzu.financecategoryapplication.income.IncomeActivity
import com.sumuzu.financecategoryapplication.model.getDataBalance.ResponseGetDataBalance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        supportActionBar!!.title = "Finance Category"

        btnKategori.setOnClickListener {
            val intent = Intent(this, KategoriActivity::class.java)
            startActivity(intent)
        }

        btnIncome.setOnClickListener {
            val intent = Intent(this, IncomeActivity::class.java)
            startActivity(intent)
        }

        btnExpenses.setOnClickListener {
            val intent = Intent(this, ExpensesActivity::class.java)
            startActivity(intent)
        }

        if(isConnect()){
            showData()
        }else{
            tvBalance.text = "Balance tidak tersedia, mohon periksa internet Anda"
            Toast.makeText(this,"device tidak connect dengan intenet",Toast.LENGTH_SHORT).show()
        }

    }

    private fun showData() {

        val listIncome = NetworkModule.serviceBalance().getDataBalance()
        listIncome.enqueue(object : Callback<ResponseGetDataBalance> {
            override fun onResponse(
                call: Call<ResponseGetDataBalance>,
                response: Response<ResponseGetDataBalance>
            ) {
                Log.d("response service", response.message())

                if(response.isSuccessful){

                    val item = response.body()?.data
                    tvBalance.text = "Balance: " + response.body()?.data

                }
            }

            override fun onFailure(call: Call<ResponseGetDataBalance>, t: Throwable) {
                Log.d("error response service", t.message)

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

    fun isConnect() : Boolean{
        val connect : ConnectivityManager =getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connect.activeNetworkInfo != null && connect.activeNetworkInfo.isConnected
    }

}