package com.sumuzu.financecategoryapplication.category

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sumuzu.financecategoryapplication.R
import com.sumuzu.financecategoryapplication.adapter.DataKategoriAdapter
import com.sumuzu.financecategoryapplication.config.NetworkModule
import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataKategori.DataItemKategori
import com.sumuzu.financecategoryapplication.model.getDataKategori.ResponseGetDataKategori
import kotlinx.android.synthetic.main.activity_kategori.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KategoriActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)

        supportActionBar!!.title = "List Kategori"

        fabAddKategori.setOnClickListener {
            val intent = Intent(this, InputKategoriActivity::class.java)
            startActivity(intent)
            progressKategori.visibility = View.VISIBLE
        }

        if(isConnect()){
            showData()
        }else{
            progressKategori.visibility = View.GONE
            Toast.makeText(this,"device tidak connect dengan intenet",Toast.LENGTH_SHORT).show()
        }

    }

    private fun showData() {

        val listKategori = NetworkModule.serviceKategori().getDataKategori()
        listKategori.enqueue(object : Callback<ResponseGetDataKategori> {
            override fun onResponse(
                call: Call<ResponseGetDataKategori>,
                response: Response<ResponseGetDataKategori>
            ) {
                Log.d("response service", response.message())

                if(response.isSuccessful){

                    val item = response.body()?.data

                    val adapter = DataKategoriAdapter(item, object : DataKategoriAdapter.OnClickListener{
                        override fun detail(item: DataItemKategori?) {
                            val intent = Intent(applicationContext, InputKategoriActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)
                        }

                        override fun delete(item: DataItemKategori?) {
                            AlertDialog.Builder(this@KategoriActivity).apply {
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

                    progressKategori.visibility = View.GONE
                    rvListKategori.adapter = adapter

                }
            }

            override fun onFailure(call: Call<ResponseGetDataKategori>, t: Throwable) {
                Log.d("error response service", t.message)

                progressKategori.visibility = View.GONE

                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun deleteData(id: String?) {
        val delete = NetworkModule.serviceKategori().deleteData(id ?:"")
        delete.enqueue(object : Callback<ResponseAction>{
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
                Toast.makeText(applicationContext, "Kategori masih digunakan di modul lain", Toast.LENGTH_LONG).show()
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