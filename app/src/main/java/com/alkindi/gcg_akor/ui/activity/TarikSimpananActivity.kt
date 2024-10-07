package com.alkindi.gcg_akor.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alkindi.gcg_akor.R
import com.alkindi.gcg_akor.databinding.ActivityTarikSimpananBinding
import com.alkindi.gcg_akor.ui.fragment.DetailSimpananFragment
import com.alkindi.gcg_akor.ui.fragment.RiwayatTransaksiFragment

class TarikSimpananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTarikSimpananBinding
    private var isNominalVisible = true

    private val dummyNominal = "Rp 12.000.000"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarikSimpananBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentDetailSimpanan, RiwayatTransaksiFragment()).commit()

        binding.btnTarikSimpanan.setOnClickListener {
            val toTarikNominal =
                Intent(this@TarikSimpananActivity, TarikNominalSimpananActivity::class.java)
            startActivity(toTarikNominal)
        }

        binding.btnDetailSimpanan.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentDetailSimpanan, DetailSimpananFragment(), "frag_detail")
                .commit()

            binding.btnDetailSimpanan.text = "Riwayat Simpanan"
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvNominalSimpananSukarela.text = dummyNominal

        binding.btnNominalVisibility.setOnClickListener {
            toggleNominalVisibility()
        }
    }

    private fun toggleNominalVisibility() {
        if (isNominalVisible) {
            binding.tvNominalSimpananSukarela.text = "............."
            binding.btnNominalVisibility.setImageResource(R.drawable.baseline_visibility_24)
        } else {
            binding.tvNominalSimpananSukarela.text = dummyNominal
            binding.btnNominalVisibility.setImageResource(R.drawable.baseline_visibility_off_24)
        }
        isNominalVisible = !isNominalVisible
    }

}