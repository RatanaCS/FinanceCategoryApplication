package com.sumuzu.financecategoryapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumuzu.financecategoryapplication.R
import com.sumuzu.financecategoryapplication.model.getDataIncome.DataItemIncome
import kotlinx.android.synthetic.main.list_item_income.view.*

class DataIncomeAdapter(val data :List<DataItemIncome>?, val itemClick : OnClickListener):
    RecyclerView.Adapter<DataIncomeAdapter.ViewHolder>() {
    class ViewHolder(val view : View): RecyclerView.ViewHolder(view) {

        val idIncome =view.tvIdIncome
        val kategori = view.tvKategori
        val nominal = view.tvNominal
        val tgl_transaksi = view.tvTglTransaksi

        val delete = view.ivDelete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_income, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : DataItemIncome? = data?.get(position)

        holder.idIncome.text = item?.id
        holder.kategori.text = item?.nama
        holder.nominal.text = item?.nominal.toString()
        holder.tgl_transaksi.text = item?.tgl_transaksi

        holder.view.setOnClickListener {
            itemClick.detail(item)
        }

        holder.delete.setOnClickListener {
            itemClick.delete(item)
        }
    }

    override fun getItemCount(): Int = data?.size ?:0

    interface OnClickListener{
        fun detail(item: DataItemIncome?)
        fun delete(item: DataItemIncome?)
    }
}