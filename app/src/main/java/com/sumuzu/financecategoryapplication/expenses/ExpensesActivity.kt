package com.sumuzu.financecategoryapplication.expenses

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.sumuzu.financecategoryapplication.R
import com.sumuzu.financecategoryapplication.adapter.DataExpensesAdapter
import com.sumuzu.financecategoryapplication.config.NetworkModule
import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataExpenses.DataItemExpenses
import com.sumuzu.financecategoryapplication.model.getDataExpenses.ResponseGetDataExpenses
import kotlinx.android.synthetic.main.activity_expenses.*
import kotlinx.android.synthetic.main.activity_expenses.tvJumlahNominal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExpensesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        supportActionBar!!.title = "List Expenses"

        fabAddExpenses.setOnClickListener {
            val intent = Intent(this, InputExpensesActivity::class.java)
            startActivity(intent)
            progressExpenses.visibility = View.VISIBLE
        }

        if(isConnect()){

            showData()
        }else{
            progressExpenses.visibility = View.GONE
            tvJumlahNominal.text = "Total Income tidak tersedia, mohon periksa internet Anda"
            Toast.makeText(this,"device tidak connect dengan intenet",Toast.LENGTH_SHORT).show()
        }


    }

    private fun showData() {

        val listExpenses = NetworkModule.serviceExpenses().getDataExpenses()
        listExpenses.enqueue(object : Callback<ResponseGetDataExpenses> {
            override fun onResponse(
                call: Call<ResponseGetDataExpenses>,
                response: Response<ResponseGetDataExpenses>
            ) {
                Log.d("response service", response.message())

                if(response.isSuccessful){

                    val item = response.body()?.data

                    tvJumlahNominal.text = "Total Expenses: " + response.body()?.subtot

                    val adapter = DataExpensesAdapter(item, object : DataExpensesAdapter.OnClickListener{
                        override fun detail(item: DataItemExpenses?) {
                            val intent = Intent(applicationContext, InputExpensesActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)
                        }

                        override fun delete(item: DataItemExpenses?) {
                            AlertDialog.Builder(this@ExpensesActivity).apply {
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

                    progressExpenses.visibility = View.GONE
                    rvListExpenses.adapter = adapter

                }
            }

            override fun onFailure(call: Call<ResponseGetDataExpenses>, t: Throwable) {
                Log.d("error response service", t.message)

                progressExpenses.visibility = View.GONE

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun deleteData(id: String?) {
        val delete = NetworkModule.serviceExpenses().deleteData(id ?:"")
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

    private fun showFilterExpenses(kategori: String) {

        val listExpenses = NetworkModule.serviceExpenses().getDataExpensesByCategory("$kategori")
        listExpenses.enqueue(object : Callback<ResponseGetDataExpenses> {
            override fun onResponse(
                call: Call<ResponseGetDataExpenses>,
                response: Response<ResponseGetDataExpenses>
            ) {
                Log.d("response service", response.message())

                if(response.isSuccessful){

                    val item = response.body()?.data
                    tvJumlahNominal.text = "Total Expenses: " + response.body()?.subtot

                    val adapter = DataExpensesAdapter(item, object : DataExpensesAdapter.OnClickListener{
                        override fun detail(item: DataItemExpenses?) {
                            val intent = Intent(applicationContext, InputExpensesActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)
                        }

                        override fun delete(item: DataItemExpenses?) {
                            AlertDialog.Builder(this@ExpensesActivity).apply {
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

                    progressExpenses.visibility = View.GONE
                    rvListExpenses.adapter = adapter

                }
            }

            override fun onFailure(call: Call<ResponseGetDataExpenses>, t: Throwable) {
                Log.d("error response service", t.message)

                progressExpenses.visibility = View.GONE

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

                        rvListExpenses.adapter!!.notifyDataSetChanged()
                    }else{

                        showFilterExpenses(newText)

                        rvListExpenses.adapter!!.notifyDataSetChanged()
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