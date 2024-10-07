package com.alkindi.gcg_akor.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alkindi.gcg_akor.databinding.ActivityPinjamanBinding
import com.alkindi.gcg_akor.utils.FormatterAngka
import com.google.android.material.chip.Chip

class PinjamanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPinjamanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinjamanBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnAturNominalLogic()
        chipButtonLogic()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun btnAturNominalLogic() {

        binding.btnTambahPinjaman?.setOnClickListener {
            tambahNilaiPinjaman()
        }

        binding.btnKrgPinjaman?.setOnClickListener {
            kurangiNilaiPinjaman()
        }

        if (binding.tvJumlahNominal?.text == "0") {
            binding.btnKrgPinjaman?.visibility = View.GONE
        }
    }

    private fun chipButtonLogic() {
        binding.chip1jt?.setOnClickListener {
            updateNominalValue(binding.chip1jt!!)
        }
        binding.chip5jt?.setOnClickListener {
            updateNominalValue(binding.chip5jt!!)
        }
        binding.chip10jt?.setOnClickListener {
            updateNominalValue(binding.chip10jt!!)
        }
    }

    private fun updateNominalValue(chip: Chip) {
        val nilaiChip = chip.text.toString().replace("Rp", "").replace(".", "")
        val formattedValue = FormatterAngka.formatterAngkaRibuan(nilaiChip.toInt())
        binding.tvJumlahNominal?.text = formattedValue
    }

    private fun tambahNilaiPinjaman() {
        val tvNumberValue = binding.tvJumlahNominal?.text
        val currentNumber = tvNumberValue.toString()
        val newNumber = FormatterAngka.formatterRibuanKeInt(currentNumber) + 10000
        binding.tvJumlahNominal?.text = FormatterAngka.formatterAngkaRibuan(newNumber)
        binding.btnKrgPinjaman?.visibility = View.VISIBLE
    }

    private fun kurangiNilaiPinjaman() {
        val tvNumberValue = binding.tvJumlahNominal?.text
        val currentNumber = tvNumberValue.toString()
        val newNumber = FormatterAngka.formatterRibuanKeInt(currentNumber) - 10000
        binding.tvJumlahNominal?.text = FormatterAngka.formatterAngkaRibuan(newNumber)
        if (binding.tvJumlahNominal?.text == "0") {
            binding.btnKrgPinjaman?.visibility = View.GONE
        }
    }
}