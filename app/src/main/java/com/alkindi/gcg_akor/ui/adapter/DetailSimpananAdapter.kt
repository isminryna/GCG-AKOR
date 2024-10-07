package com.alkindi.gcg_akor.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkindi.gcg_akor.data.model.DetailSimpananModel
import com.alkindi.gcg_akor.databinding.RvDetailsimpananCardBinding

class DetailSimpananAdapter :
    ListAdapter<DetailSimpananModel, DetailSimpananAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: RvDetailsimpananCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailSimpananModel) {
            binding.tvSimpananSukarela.text = item.jenisDana
            binding.tglTransaksi.text = item.tgl
            binding.tvNominalDetail.text = item.nominal
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailSimpananModel>() {
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: DetailSimpananModel,
                newItem: DetailSimpananModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: DetailSimpananModel,
                newItem: DetailSimpananModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}