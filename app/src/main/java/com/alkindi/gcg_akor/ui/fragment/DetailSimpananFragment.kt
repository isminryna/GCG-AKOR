package com.alkindi.gcg_akor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkindi.gcg_akor.R
import com.alkindi.gcg_akor.data.model.DetailSimpananModel
import com.alkindi.gcg_akor.databinding.FragmentDetailSimpananBinding
import com.alkindi.gcg_akor.ui.adapter.DetailSimpananAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DetailSimpananFragment : Fragment() {
    private val list = ArrayList<DetailSimpananModel>()
    private lateinit var binding: FragmentDetailSimpananBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailSimpananBinding.inflate(inflater, container, false)
        val view = binding.root

        showRVData()

        return view
    }

    private fun showRVData() {
        binding.rvDetailSimpanan.layoutManager = LinearLayoutManager(requireActivity())
        list.addAll(getDetailData())
        val adapter = DetailSimpananAdapter()
        adapter.submitList(list)
        binding.rvDetailSimpanan.adapter = adapter
    }

    private fun getDetailData(): ArrayList<DetailSimpananModel> {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))
        val currentDate = sdf.format(Date())

        val jenisDana = resources.getStringArray(R.array.tipe_transaksi)
        val nominal = resources.getStringArray(R.array.nominal)

        val listDetail = ArrayList<DetailSimpananModel>()
        for (i in jenisDana.indices) {
            val data = DetailSimpananModel(
                jenisDana[i],
                nominal[i],
                currentDate
            )
            listDetail.add(data)
        }
        return listDetail
    }

}