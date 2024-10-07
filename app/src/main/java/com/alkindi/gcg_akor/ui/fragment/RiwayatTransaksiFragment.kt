package com.alkindi.gcg_akor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkindi.gcg_akor.R
import com.alkindi.gcg_akor.data.model.RiwayatTransaksiModel
import com.alkindi.gcg_akor.databinding.FragmentRiwayatTransaksiBinding
import com.alkindi.gcg_akor.ui.adapter.RiwayatTransaksiAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class RiwayatTransaksiFragment : Fragment() {
    private lateinit var binding: FragmentRiwayatTransaksiBinding
    private val list = ArrayList<RiwayatTransaksiModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRiwayatTransaksiBinding.inflate(inflater, container, false)
        val view = binding.root

        showListRiwayat()
        return view
    }

    private fun showListRiwayat() {
        binding.rvRiwayatTransaksi.layoutManager = LinearLayoutManager(requireActivity())
        list.addAll(getRiwayatData())
        val adapter = RiwayatTransaksiAdapter()
        adapter.submitList(list)
        binding.rvRiwayatTransaksi.adapter = adapter
    }

    private fun getRiwayatData(): ArrayList<RiwayatTransaksiModel> {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))
        val currentDate = sdf.format(Date())

        val jenisTransaksi = resources.getStringArray(R.array.jenis_transaksi)
        val tipeTransaksi = resources.getStringArray(R.array.tipe_transaksi)
        val idTransaksi = resources.getStringArray(R.array.id_transaksi)
        val nominalTransaksi = resources.getStringArray(R.array.nominal)

        val listRiwayat = ArrayList<RiwayatTransaksiModel>()
        for (i in jenisTransaksi.indices) {
            val data = RiwayatTransaksiModel(
                jenisTransaksi[i],
                tipeTransaksi[i],
                idTransaksi[i],
                nominalTransaksi[i],
                currentDate
            )
            listRiwayat.add(data)
        }
        return listRiwayat
    }

}