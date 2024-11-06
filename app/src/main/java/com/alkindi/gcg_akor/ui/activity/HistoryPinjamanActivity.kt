package com.alkindi.gcg_akor.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkindi.gcg_akor.R
import com.alkindi.gcg_akor.data.model.HistoryPinjamanModel
import com.alkindi.gcg_akor.data.model.ViewModelFactory
import com.alkindi.gcg_akor.databinding.ActivityHistoryPinjamanBinding
import com.alkindi.gcg_akor.ui.adapter.HistoryPinjamanAdapter
import com.alkindi.gcg_akor.ui.viewmodel.HistoryPinjamanViewModel

class HistoryPinjamanActivity : AppCompatActivity() {
    private val list = ArrayList<HistoryPinjamanModel>()
    private lateinit var binding: ActivityHistoryPinjamanBinding
    private val historyPinjamanViewModel: HistoryPinjamanViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPinjamanBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showHistoryPinjamanData()
        checkLoading()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun checkLoading() {
        historyPinjamanViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }


    private fun showHistoryPinjamanData() {
        historyPinjamanViewModel.getSession().observe(this) {
            historyPinjamanViewModel.getHistoryData(it.username)

            historyPinjamanViewModel.historyPinjamanResponse.observe(this) {
                binding.rvHistoryPinjaman.layoutManager = LinearLayoutManager(this)
                val adapter = HistoryPinjamanAdapter()
                adapter.submitList(it.data)
                binding.rvHistoryPinjaman.adapter = adapter
            }
        }

//        list.addAll(getHistoryPinjamanData())
//        val adapter = HistoryPinjamanAdapter()
//        adapter.submitList(list)
//        binding.rvHistoryPinjaman.adapter = adapter
    }


    private fun getHistoryPinjamanData(): ArrayList<HistoryPinjamanModel> {
        val nominalGaji = resources.getStringArray(R.array.history_pinjaman_gaji)
        val historyPinjamanBulanan = resources.getStringArray(R.array.history_pinjaman_bulanan)
        val historyPinjamanTenor = resources.getStringArray(R.array.history_pinjaman_tenor)
        val uniqueID = resources.getStringArray(R.array.history_pinjaman_id)

        val listDatas = ArrayList<HistoryPinjamanModel>()
        for (i in uniqueID.indices) {
            val data = HistoryPinjamanModel(
                uniqueID[i],
                nominalGaji[i],
                historyPinjamanBulanan[i],
                historyPinjamanTenor[i]
            )
            listDatas.add(data)
        }
        return listDatas
    }
}