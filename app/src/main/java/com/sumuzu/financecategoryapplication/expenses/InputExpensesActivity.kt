package com.sumuzu.financecategoryapplication.expenses

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sumuzu.financecategoryapplication.R
import com.sumuzu.financecategoryapplication.config.NetworkModule
import com.sumuzu.financecategoryapplication.model.ResponseAction
import com.sumuzu.financecategoryapplication.model.getDataExpenses.DataItemExpenses
import com.sumuzu.financecategoryapplication.utils.CalendarUtil
import kotlinx.android.synthetic.main.activity_input_expenses.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputExpensesActivity : AppCompatActivity() {

    private var datePicker : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_expenses)

        supportActionBar!!.title = "Input Expenses"

        etId.visibility = View.GONE
        etNominal.visibility = View.GONE

        etDate.setOnClickListener {
            val calender = CalendarUtil
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, month, day ->
                datePicker = "$year-${(month+1)}-$day"
                etDate.setText(datePicker)
            }, calender.year, calender.month, calender.day)
            datePickerDialog.show()
        }


        val getData = intent.getParcelableExtra<DataItemExpenses>("data")

        if(getData != null){

            etId.visibility = View.VISIBLE
            etNominal.visibility = View.VISIBLE
            etDate.setEnabled(false)

            etId.setText(getData.id)
            etKategori.setText(getData.nama)
            etJumlah.setText(getData.jumlah.toString())
            etNominal.setText(getData.nominal.toString())
            etDate.setText(getData.tgl_transaksi)

            btnSimpan.text = "Update"
            supportActionBar!!.title = "Update Expenses"
        }

        when(btnSimpan.text){
            "Update" ->{
                btnSimpan.setOnClickListener {
                    if(etKategori.text.isNullOrEmpty() || etDate.text.isNullOrEmpty() || etJumlah.text.isNullOrEmpty()){
                        Toast.makeText(applicationContext, "Nama Kategori, Jumlah dan Tanggal Transaksi harus terisi!!", Toast.LENGTH_LONG).show()
                    }else{
                        updateData(getData?.id, etKategori.text.toString(), etJumlah.text.toString(), etDate.text.toString())
                    }
                }
            }
            else -> {
                btnSimpan.setOnClickListener {
                    if(etKategori.text.isNullOrEmpty() || etDate.text.isNullOrEmpty() || etJumlah.text.isNullOrEmpty()){
                        Toast.makeText(applicationContext, "Nama Kategori, Jumlah dan Tanggal Transaksi harus terisi!!", Toast.LENGTH_LONG).show()
                    }else{
                        inputData(etKategori.text.toString(), etJumlah.text.toString(), etDate.text.toString())
                    }
                }
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }

    }

    private fun updateData(id: String?, kategori: String?, jumlah: String?, tgl_transaksi : String?) {
        val update = NetworkModule.serviceExpenses().updateData(id ?:"",kategori ?:"", jumlah ?:"0", tgl_transaksi ?:"2020-12-20")
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

    private fun inputData(kategori: String?, jumlah: String?, tgl_transaksi : String?) {
        val expenses = NetworkModule.serviceExpenses().insertData(kategori ?:"", jumlah ?:"0", tgl_transaksi ?:"2020-12-20")
        expenses.enqueue(object : Callback<ResponseAction> {
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