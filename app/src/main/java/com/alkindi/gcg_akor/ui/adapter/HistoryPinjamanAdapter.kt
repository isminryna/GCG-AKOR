package com.alkindi.gcg_akor.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkindi.gcg_akor.data.model.HistoryPinjamanModel
import com.alkindi.gcg_akor.databinding.RvHistorypinjamanCardBinding

class HistoryPinjamanAdapter :
    ListAdapter<HistoryPinjamanModel, HistoryPinjamanAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvHistorypinjamanCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryPinjamanModel) {
            binding.tvUniqueId.text = item.uniqueId
            binding.tvNominalGaji.text = item.nominalGaji
            binding.tvNominalPinjamanBulanan.text = item.pinjamanBulanan
            binding.tvSisaTenor.text = item.tenor
        }
    }


    companion object {
        @SuppressLint("DiffUtilEquals")
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryPinjamanModel>() {
            override fun areContentsTheSame(
                oldItem: HistoryPinjamanModel,
                newItem: HistoryPinjamanModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: HistoryPinjamanModel,
                newItem: HistoryPinjamanModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvHistorypinjamanCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val historyData = getItem(position)
        holder.bind(historyData)
    }


}