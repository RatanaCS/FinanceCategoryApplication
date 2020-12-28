package com.sumuzu.financecategoryapplication.income

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.view.View
import android.widget.Toast
import com.sumuzu.financecategoryapplication.R
import com.sumuzu.financecategoryapplication.adapter.DataIncomeAdapter
import com.sumuzu.financecategoryapplication.config.NetworkModule
import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataIncome.DataItemIncome
import com.sumuzu.financecategoryapplication.model.getDataIncome.ResponseGetDataIncome
import kotlinx.android.synthetic.main.activity_income.*
import kotlinx.android.synthetic.main.activity_income.tvJumlahNominal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class IncomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        supportActionBar!!.title = "List Income"

        fabAddIncome.setOnClickListener {
            val intent = Intent(this, InputIncomeActivity::class.java)
            startActivity(intent)
            progressIncome.visibility = View.VISIBLE
        }

        if(isConnect()){
            showData()
        }else{
            progressIncome.visibility = View.GONE
            tvJumlahNominal.text = "Total Income tidak tersedia, mohon periksa internet Anda"
            Toast.makeText(this,"device tidak connect dengan intenet",Toast.LENGTH_SHORT).show()
        }



    }

    private fun showData() {

        val listIncome = NetworkModule.serviceIncome().getDataIncome()
        listIncome.enqueue(object : Callback<ResponseGetDataIncome> {
            override fun onResponse(
                call: Call<ResponseGetDataIncome>,
                response: Response<ResponseGetDataIncome>
            ) {
                Log.d("response service", response.message())

                if(response.isSuccessful){

                    val item = response.body()?.data
                    tvJumlahNominal.text = "Total Income: " + response.body()?.subtot

                    val adapter = DataIncomeAdapter(item, object : DataIncomeAdapter.OnClickListener{
                        override fun detail(item: DataItemIncome?) {
                            val intent = Intent(applicationContext, InputIncomeActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)
                        }

                        override fun delete(item: DataItemIncome?) {
                            AlertDialog.Builder(this@IncomeActivity).apply {
                                setTitle("Hapus Data")
                                setMessage("Yakin mau hapus data??")
                                setPositiveButton("Hapus"){dialog, which->
                                    deleteData(item?.id)
                                    dialog.dismiss()
                                }
                                setNegativeButton("Batal"){dialog, which ->
                                    dialog.dismiss()
                                }
                            }.show()
                        }

                    })

                    progressIncome.visibility = View.GONE
                    rvListIncome.adapter = adapter

                }
            }

            override fun onFailure(call: Call<ResponseGetDataIncome>, t: Throwable) {
                Log.d("error response service", t.message)

                progressIncome.visibility = View.GONE

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun deleteData(id: String?) {
        val delete = NetworkModule.serviceIncome().deleteData(id ?:"")
        delete.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "Data berhasil diHAPUS", Toast.LENGTH_LONG).show()
                    showData()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        showData()
    }


    /////////////////Filter

    private fun showFilterIncome(kategori: String) {

        val listIncome = NetworkModule.serviceIncome().getDataIncomeByCategory("$kategori")
        listIncome.enqueue(object : Callback<ResponseGetDataIncome> {
            override fun onResponse(
                call: Call<ResponseGetDataIncome>,
                response: Response<ResponseGetDataIncome>
            ) {
                Log.d("response service", response.message())

                if(response.isSuccessful){

                    val item = response.body()?.data
                    tvJumlahNominal.text = "Total Income: " + response.body()?.subtot

                    val adapter = DataIncomeAdapter(item, object : DataIncomeAdapter.OnClickListener{
                        override fun detail(item: DataItemIncome?) {
                            val intent = Intent(applicationContext, InputIncomeActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)
                        }

                        override fun delete(item: DataItemIncome?) {
                            AlertDialog.Builder(this@IncomeActivity).apply {
                                setTitle("Hapus Data")
                                setMessage("Yakin mau hapus data??")
                                setPositiveButton("Hapus"){dialog, which->
                                    deleteData(item?.id)
                                    dialog.dismiss()
                                }
                                setNegativeButton("Batal"){dialog, which ->
                                    dialog.dismiss()
                                }
                            }.show()
                        }

                    })

                    progressIncome.visibility = View.GONE
                    rvListIncome.adapter = adapter

                }
            }

            override fun onFailure(call: Call<ResponseGetDataIncome>, t: Throwable) {
                Log.d("error response service", t.message)

                progressIncome.visibility = View.GONE

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu!!.findItem(R.id.action_search)

        if(menuItem != null){
            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText!!.isEmpty()){

                        showData()

                        rvListIncome.adapter!!.notifyDataSetChanged()
                    }else{

                        showFilterIncome(newText)

                        rvListIncome.adapter!!.notifyDataSetChanged()
                    }

                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    fun isConnect() : Boolean{
        val connect : ConnectivityManager =getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connect.activeNetworkInfo != null && connect.activeNetworkInfo.isConnected
    }

}