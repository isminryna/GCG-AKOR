package com.alkindi.gcg_akor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkindi.gcg_akor.data.model.RiwayatTransaksiModel
import com.alkindi.gcg_akor.data.model.ViewModelFactory
import com.alkindi.gcg_akor.databinding.FragmentRiwayatTransaksiBinding
import com.alkindi.gcg_akor.ui.adapter.RiwayatTransaksiAdapter
import com.alkindi.gcg_akor.ui.viewmodel.RiwayatTransaksiFragmentViewModel
import kotlinx.coroutines.launch


class RiwayatTransaksiFragment : Fragment() {
    private lateinit var binding: FragmentRiwayatTransaksiBinding
    private val userID = "10006"
    private val riwayatTransaksiFragmentViewModel: RiwayatTransaksiFragmentViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val list = ArrayList<RiwayatTransaksiModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRiwayatTransaksiBinding.inflate(inflater, container, false)
        val view = binding.root
//        riwayatTransaksiFragmentViewModel.getSession().observe(viewLifecycleOwner) {
//            userID = it.username
//        }
        showListRiwayat()
        return view
    }

    private fun showListRiwayat() {
        lifecycleScope.launch {
            riwayatTransaksiFragmentViewModel.getHistoryTarikSimp(userID)
        }
        riwayatTransaksiFragmentViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        riwayatTransaksiFragmentViewModel.historyTarikSimpResponse.observe(viewLifecycleOwner) { resp ->
            val jenisTransaksi = resp[0].stp ?: "Kosong"
            val nominal = resp[0].amount ?: "Kosong"
            val transactionDate = resp[0].transDate ?: "Kosong"
            val transDoc = resp[0].docNum ?: "Kosong"
            val data = RiwayatTransaksiModel(
                jenisTransaksi,
                transDoc,
                nominal,
                transactionDate
            )
            list.add(data)
            binding.rvRiwayatTransaksi.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = RiwayatTransaksiAdapter()
            adapter.submitList(list)
            binding.rvRiwayatTransaksi.adapter = adapter
        }


        binding.rvRiwayatTransaksi.layoutManager = LinearLayoutManager(requireActivity())
        list.addAll(list)
        val adapter = RiwayatTransaksiAdapter()
        adapter.submitList(list)
        binding.rvRiwayatTransaksi.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getRiwayatData() {
        riwayatTransaksiFragmentViewModel.historyTarikSimpResponse.observe(viewLifecycleOwner) { resp ->
            val jenisTransaksi = resp[0].stp ?: "Kosong"
            val nominal = resp[0].amount ?: "Kosong"
            val transactionDate = resp[0].transDate ?: "Kosong"
            val transDoc = resp[0].docNum ?: "Kosong"

            val listRiwayat = ArrayList<RiwayatTransaksiModel>()

            val data = RiwayatTransaksiModel(
                jenisTransaksi,
                transDoc,
                nominal,
                transactionDate
            )
            list.add(data)
        }

//        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))
//        val currentDate = sdf.format(Date())
//
//        val jenisTransaksi = resources.getStringArray(R.array.jenis_transaksi)
//        val tipeTransaksi = resources.getStringArray(R.array.tipe_transaksi)
//        val idTransaksi = resources.getStringArray(R.array.id_transaksi)
//        val nominalTransaksi = resources.getStringArray(R.array.nominal)
//
//        val listRiwayat = ArrayList<RiwayatTransaksiModel>()
//        for (i in jenisTransaksi.indices) {
//            val data = RiwayatTransaksiModel(
//                jenisTransaksi[i],
////                tipeTransaksi[i],
//                idTransaksi[i],
//                nominalTransaksi[i],
//                currentDate
//            )
//            listRiwayat.add(data)
//        }
//        return listRiwayat
    }

}