package com.alkindi.gcg_akor.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkindi.gcg_akor.data.model.RiwayatTransaksiHomeModel
import com.alkindi.gcg_akor.databinding.RvRiwayattransaksiHomeCardBinding
import com.bumptech.glide.Glide

class RiwayatTransaksiHomeAdapter :
    ListAdapter<RiwayatTransaksiHomeModel, RiwayatTransaksiHomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvRiwayattransaksiHomeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RiwayatTransaksiHomeModel) {

            binding.tvJenisTransaksi.text = item.jenisTransaksi
            binding.tvTipeTransaksi.text = item.tipeTransaksi
            Glide.with(itemView.context).load(item.status).into(binding.icStatus)
            binding.tvTanggal.text = item.tglTransaksi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvRiwayattransaksiHomeCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val historyData = getItem(position)
        holder.bind(historyData)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RiwayatTransaksiHomeModel>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: RiwayatTransaksiHomeModel,
                newItem: RiwayatTransaksiHomeModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: RiwayatTransaksiHomeModel,
                newItem: RiwayatTransaksiHomeModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}