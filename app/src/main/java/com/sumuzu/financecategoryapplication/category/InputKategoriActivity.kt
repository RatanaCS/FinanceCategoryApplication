package com.sumuzu.financecategoryapplication.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sumuzu.financecategoryapplication.R
import com.sumuzu.financecategoryapplication.config.NetworkModule
import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataKategori.DataItemKategori
import kotlinx.android.synthetic.main.activity_input_kategori.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputKategoriActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_kategori)

        supportActionBar!!.title = "Input Kategori"

        val getData = intent.getParcelableExtra<DataItemKategori>("data")

        if(getData != null){

            etKategori.setText(getData.nama)
            etHBeli.setText(getData.harga_beli)
            etHJual.setText(getData.harga_jual)
            etSatuan.setText(getData.satuan)

            btnSimpan.text = "Update"
            supportActionBar!!.title = "Update Kategori"
        }

        when(btnSimpan.text){
            "Update" ->{
                btnSimpan.setOnClickListener {
                    if(etKategori.text.isNullOrEmpty() || etHBeli.text.isNullOrEmpty() || etHJual.text.isNullOrEmpty() || etSatuan.text.isNullOrEmpty()){
                        Toast.makeText(applicationContext, "Nama Kategori, Harga Beli, Harga Jual dan Satuan harus terisi!!", Toast.LENGTH_LONG).show()
                    }else{
                        updateData(getData?.id, etKategori.text.toString(), etHBeli.text.toString(), etHJual.text.toString(), etSatuan.text.toString())
                    }
                }
            }
            else -> {
                btnSimpan.setOnClickListener {
                    if(etKategori.text.isNullOrEmpty() || etHBeli.text.isNullOrEmpty() || etHJual.text.isNullOrEmpty() || etSatuan.text.isNullOrEmpty()){
                        Toast.makeText(applicationContext, "Nama, No Tlp dan Alamat harus terisi!!", Toast.LENGTH_LONG).show()
                    }else{
                        inputData(etKategori.text.toString(), etHBeli.text.toString(), etHJual.text.toString(), etSatuan.text.toString())
                    }
                }
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }

    }

    private fun updateData(id: String?, kategori: String?, harga_beli: String?, harga_jual : String?, satuan : String?) {
        val update = NetworkModule.serviceKategori().updateData(id ?:"",kategori ?:"", harga_beli ?:"0", harga_jual ?:"0", satuan ?:"")
        update.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "Data berhasil diubah", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })


    }

    private fun inputData(nama: String?, h_beli: String?, h_jual : String?, satuan : String?) {
        val input = NetworkModule.serviceKategori().insertData(nama ?:"", h_beli ?:"0", h_jual ?:"0", satuan ?:"")
        input.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}