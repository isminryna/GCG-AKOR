package com.alkindi.gcg_akor.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkindi.gcg_akor.R
import com.alkindi.gcg_akor.data.model.RiwayatTransaksiHomeModel
import com.alkindi.gcg_akor.data.model.ViewModelFactory
import com.alkindi.gcg_akor.databinding.ActivityHomeBinding
import com.alkindi.gcg_akor.ui.adapter.RiwayatTransaksiHomeAdapter
import com.alkindi.gcg_akor.ui.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private val list = ArrayList<RiwayatTransaksiHomeModel>()
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        homeViewModel.checkSavedLoginData()

        binding.btnPersonalData.setOnClickListener {
            val toPersonalData = Intent(this@HomeActivity, PersonalDataActivity::class.java)
            startActivity(toPersonalData)
        }
        binding.btnPinjaman.setOnClickListener {
            val toPinjamanActivity = Intent(this@HomeActivity, PinjamanActivity::class.java)
            startActivity(toPinjamanActivity)
        }
        binding.btnHistory.setOnClickListener {
            val toHistoryActivity = Intent(this@HomeActivity, HistoryPinjamanActivity::class.java)
            startActivity(toHistoryActivity)
        }
        binding.btnTarikSimpanan.setOnClickListener {
            val toTarikSimpanan = Intent(this@HomeActivity, TarikSimpananActivity::class.java)
            startActivity(toTarikSimpanan)
        }
        binding.btnTambahPinjaman?.setOnClickListener {
            val toTambahPinjaman = Intent(this@HomeActivity, PinjamanActivity::class.java)
            startActivity(toTambahPinjaman)
        }
        binding.imProfile.setOnClickListener {
            val toProfile = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(toProfile)
        }

//        checkSavedSession()
        getRvData()
    }

//    private fun checkSavedSession() {
//        homeViewModel.checkSavedLoginData()
//    }

    private fun getRvData() {
        binding.rvRiwayatTransaksiHome.layoutManager = LinearLayoutManager(this)
        list.addAll(getRiwayatTransaksiData())
        val adapter = RiwayatTransaksiHomeAdapter()
        adapter.submitList(list)
        binding.rvRiwayatTransaksiHome.adapter = adapter
    }

    private fun getRiwayatTransaksiData(): ArrayList<RiwayatTransaksiHomeModel> {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))
        val currentDate = sdf.format(Date())

        val jenisTransaksi = resources.getStringArray(R.array.jenis_transaksi_home)
        val tipeTransaksi = resources.getStringArray(R.array.tipe_transaksi_home)
        val status = resources.obtainTypedArray(R.array.status_transaksi_home)

        val listDatas = ArrayList<RiwayatTransaksiHomeModel>()
        for (i in jenisTransaksi.indices) {
            val data = RiwayatTransaksiHomeModel(
                jenisTransaksi[i],
                tipeTransaksi[i],
                status.getResourceId(i, -1),
                currentDate
            )
            listDatas.add(data)
        }
        return listDatas
    }
}