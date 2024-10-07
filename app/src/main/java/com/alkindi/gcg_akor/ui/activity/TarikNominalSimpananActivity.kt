package com.alkindi.gcg_akor.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alkindi.gcg_akor.databinding.ActivityTarikNominalSimpananBinding
import com.alkindi.gcg_akor.utils.FormatterAngka
import com.google.android.material.chip.Chip

class TarikNominalSimpananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTarikNominalSimpananBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTarikNominalSimpananBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        logicAturNominal()
        chipButtonLogic()

        binding.btnBack.setOnClickListener {
            finish()
        }


        binding.layoutCatatan.setOnClickListener {
            binding.edtCatatan.requestFocus()

            val keybInput =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keybInput.showSoftInput(binding.edtCatatan, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun chipButtonLogic() {
        binding.chip100rb.setOnClickListener {
            updateNominalValue(binding.chip100rb)
        }
        binding.chip300rb.setOnClickListener {
            updateNominalValue(binding.chip300rb)
        }
        binding.chip500rb.setOnClickListener {
            updateNominalValue(binding.chip500rb)
        }
    }

    private fun updateNominalValue(chip: Chip) {
        val nilaiChip = chip.text.toString().replace("Rp", "").replace(".", "").replace(" ", "")
        val formattedValue = FormatterAngka.formatterAngkaRibuan(nilaiChip.toInt())
        binding.tvNominalTarikSimpanan.text = formattedValue
    }

    private fun logicAturNominal() {
        binding.btnKurangiNominal.visibility = View.GONE

        binding.btnTambahNominal.setOnClickListener {
            tambahNominal()
        }
        binding.btnKurangiNominal.setOnClickListener {
            kurangiNominal()
        }
    }

    private fun kurangiNominal() {
        val jmlNominal = binding.tvNominalTarikSimpanan.text
        val currentNumber = jmlNominal.toString()
        val newNumber = FormatterAngka.formatterRibuanKeInt(currentNumber) - 10000
        binding.tvNominalTarikSimpanan.text = FormatterAngka.formatterAngkaRibuan(newNumber)
        if (binding.tvNominalTarikSimpanan.text == "0") {
            binding.btnKurangiNominal.visibility = View.GONE
        }
    }

    private fun tambahNominal() {
        val jmlNominal = binding.tvNominalTarikSimpanan.text
        val currentNumber = jmlNominal.toString()
        val newNumber = FormatterAngka.formatterRibuanKeInt(currentNumber) + 10000
        binding.tvNominalTarikSimpanan.text = FormatterAngka.formatterAngkaRibuan(newNumber)
        binding.btnKurangiNominal.visibility = View.VISIBLE
    }
}