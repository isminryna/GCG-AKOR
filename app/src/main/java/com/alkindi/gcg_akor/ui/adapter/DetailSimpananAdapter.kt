package com.alkindi.gcg_akor.ui.adapter

import android.annotation.SuppressLint
import android.telecom.Call.Details
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkindi.gcg_akor.data.model.DetailSimpananModel
import com.alkindi.gcg_akor.data.remote.response.DetailSimpananItem
import com.alkindi.gcg_akor.databinding.ActivityTarikSimpananBinding
import com.alkindi.gcg_akor.databinding.RvDetailsimpananCardBinding

class DetailSimpananAdapter :
    ListAdapter<DetailSimpananItem, DetailSimpananAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvDetailsimpananCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailSimpananItem) {
            binding.tvSimpananSukarela.text = item.txnCode
            binding.tglTransaksi.text = item.transDate
            binding.tvNominalDetail.text =item.amount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvDetailsimpananCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataDetail = getItem(position)
        holder.bind(dataDetail)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailSimpananItem>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: DetailSimpananItem,
                newItem: DetailSimpananItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: DetailSimpananItem,
                newItem: DetailSimpananItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}