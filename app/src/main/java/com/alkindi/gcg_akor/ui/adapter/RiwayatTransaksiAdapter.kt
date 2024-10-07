package com.alkindi.gcg_akor.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkindi.gcg_akor.data.model.RiwayatTransaksiModel
import com.alkindi.gcg_akor.databinding.RvRiwayattransaksiCardBinding

class RiwayatTransaksiAdapter :
    ListAdapter<RiwayatTransaksiModel, RiwayatTransaksiAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvRiwayattransaksiCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RiwayatTransaksiModel) {
            binding.tvTarikSimpanan.text = item.jenisTransaksi
            binding.tvSukarela.text = item.tipeTransaksi
            binding.idTransaksi.text = item.idTransaksi
            binding.tvNominalRiwayat.text = item.nominal
            binding.tglTransaksi.text =item.tglTransaksi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvRiwayattransaksiCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataRiwayat = getItem(position)
        holder.bind(dataRiwayat)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RiwayatTransaksiModel>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: RiwayatTransaksiModel,
                newItem: RiwayatTransaksiModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: RiwayatTransaksiModel,
                newItem: RiwayatTransaksiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}