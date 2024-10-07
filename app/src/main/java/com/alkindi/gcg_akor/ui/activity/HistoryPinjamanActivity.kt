package com.alkindi.gcg_akor.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkindi.gcg_akor.R
import com.alkindi.gcg_akor.data.model.HistoryPinjamanModel
import com.alkindi.gcg_akor.databinding.ActivityHistoryPinjamanBinding
import com.alkindi.gcg_akor.ui.adapter.HistoryPinjamanAdapter

class HistoryPinjamanActivity : AppCompatActivity() {
    private val list = ArrayList<HistoryPinjamanModel>()
    private lateinit var binding: ActivityHistoryPinjamanBinding

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

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showHistoryPinjamanData() {
        binding.rvHistoryPinjaman.layoutManager = LinearLayoutManager(this)
        list.addAll(getHistoryPinjamanData())
        val adapter = HistoryPinjamanAdapter()
        adapter.submitList(list)
        binding.rvHistoryPinjaman.adapter = adapter
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