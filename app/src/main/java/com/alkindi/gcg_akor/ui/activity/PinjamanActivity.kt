package com.alkindi.gcg_akor.ui.activity

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.alkindi.gcg_akor.data.model.ViewModelFactory
import com.alkindi.gcg_akor.data.remote.response.TipePotonganItem
import com.alkindi.gcg_akor.databinding.ActivityPinjamanBinding
import com.alkindi.gcg_akor.ui.viewmodel.PinjamanViewModel
import com.alkindi.gcg_akor.utils.AndroidUIHelper
import com.alkindi.gcg_akor.utils.FormatterAngka
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class PinjamanActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityPinjamanBinding
    private lateinit var filePickerSlipGaji: ActivityResultLauncher<Intent>
    private lateinit var filePickerSlipBonus: ActivityResultLauncher<Intent>
    private lateinit var filePickerSlipGaji3: ActivityResultLauncher<Intent>
    private val pinjamanViewModel: PinjamanViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

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
        checkLoading()
        observeTipePotonganData()

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnLanjutkan?.setOnClickListener {
            val toInputNominal = Intent(this@PinjamanActivity, NominalPinjamanActivity::class.java)
            startActivity(toInputNominal)
        }

        binding.spinnerTipePinjaman?.onItemSelectedListener = this

        filePickerSlipBonus =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == Activity.RESULT_OK && res.data != null) {
                    res.data?.data?.let { uri ->
                        val fileName = getFileName(uri)
                        binding.edtSlipBonusFilename?.setText(fileName)
                    }
                }
            }

        filePickerSlipGaji =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == Activity.RESULT_OK && res.data != null) {
                    res.data?.data?.let { uri ->
                        val filename = getFileName(uri)
                        binding.edtSlipGajiFileName?.setText(filename)
                    }
                }
            }

        filePickerSlipGaji3 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
                if (res.resultCode == Activity.RESULT_OK && res.data != null) {
                    res.data?.data?.let { uri ->
                        val filename = getFileName(uri)
                        binding.edtSlipGaji3Filename?.setText(filename)
                    }
                }
            }

        binding.btnUploadSlipGaji?.setOnClickListener {
//            openFilePickerForUploadSlipGaji()
        }

        binding.btnUploadSlipBonus?.setOnClickListener {
//            openFilePickerForUploadSlipBonus()
        }
        binding.btnUploadGaji3?.setOnClickListener {
//            openFilePickerForUploadGaji3()
        }
    }

    private fun observeTipePotonganData() {
        pinjamanViewModel.listPotonganResponse.observe(this) { resp ->
            resp?.let {
                if (it.code == 200 && it.data != null) {
                    addSpinnerData(it.data)
                } else {
                    AndroidUIHelper.showWarningToastShort(this, "Failed to load data")
                }
            }
        }
    }

    private fun addSpinnerData(data: List<TipePotonganItem?>) {
        val listPitCode = data.mapNotNull { it?.pitcode }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listPitCode)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipePotongan?.adapter = spinnerAdapter
    }

    private fun checkLoading() {
        pinjamanViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar?.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            binding.progressBar?.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        result = it.getString(nameIndex)
                    }
                }
            }
        }
        return result ?: uri.path?.substringAfterLast('/') ?: "Unknown"
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

    override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
        val selectedItem: String = parent?.getItemAtPosition(position).toString()

        when (selectedItem) {
            "Jangka Panjang" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("JAPAN")
                }
                binding.tvSimpananPagu?.visibility = View.VISIBLE
                binding.edtSimpananPagu?.visibility = View.VISIBLE
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAdministrasi?.visibility = View.VISIBLE
                binding.edtAdministrasi?.visibility = View.VISIBLE
                binding.tvAsuransi?.visibility = View.VISIBLE
                binding.edtAsuransi?.visibility = View.VISIBLE
                binding.tvProvisi?.visibility = View.VISIBLE
                binding.edtProvinsi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvTglPencairan?.visibility = View.GONE
                binding.edtTglPencairan?.visibility = View.GONE
                binding.tvTglBonus?.visibility = View.GONE
                binding.edtTglBonus?.visibility = View.GONE
            }

            "Rumah" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("RUMAH")
                }
                binding.tvSimpananPagu?.visibility = View.VISIBLE
                binding.edtSimpananPagu?.visibility = View.VISIBLE
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAdministrasi?.visibility = View.VISIBLE
                binding.edtAdministrasi?.visibility = View.VISIBLE
                binding.tvAsuransi?.visibility = View.VISIBLE
                binding.edtAsuransi?.visibility = View.VISIBLE
                binding.tvProvisi?.visibility = View.VISIBLE
                binding.edtProvinsi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvTglPencairan?.visibility = View.GONE
                binding.edtTglPencairan?.visibility = View.GONE
                binding.tvTglBonus?.visibility = View.GONE
                binding.edtTglBonus?.visibility = View.GONE
            }

            "Kredit Barang" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("BRANG")
                }
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvSimpananPagu?.visibility = View.GONE
                binding.edtSimpananPagu?.visibility = View.GONE
                binding.tvAdministrasi?.visibility = View.GONE
                binding.edtAdministrasi?.visibility = View.GONE
                binding.tvAsuransi?.visibility = View.GONE
                binding.edtAsuransi?.visibility = View.GONE
                binding.tvProvisi?.visibility = View.GONE
                binding.edtProvinsi?.visibility = View.GONE
            }

            "Mobil" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("MOBIL")
                }
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAdministrasi?.visibility = View.VISIBLE
                binding.edtAdministrasi?.visibility = View.VISIBLE
                binding.tvAsuransi?.visibility = View.VISIBLE
                binding.edtAsuransi?.visibility = View.VISIBLE
                binding.tvProvisi?.visibility = View.VISIBLE
                binding.edtProvinsi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvSimpananPagu?.visibility = View.GONE
                binding.edtSimpananPagu?.visibility = View.GONE
//                binding.tvSaldoPinjaman?.visibility = View.GONE
//                binding.edtSaldoPinjaman?.visibility = View.GONE
            }

            "Motor" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("RUMAH")
                }
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAsuransi?.visibility = View.VISIBLE
                binding.edtAsuransi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvSimpananPagu?.visibility = View.GONE
                binding.edtSimpananPagu?.visibility = View.GONE
                binding.tvAdministrasi?.visibility = View.GONE
                binding.edtAdministrasi?.visibility = View.GONE
                binding.tvProvisi?.visibility = View.GONE
                binding.edtProvinsi?.visibility = View.GONE
            }

            "Jangka Pendek" -> {
                lifecycleScope.launch {
                    pinjamanViewModel.getTipePotonganList("JAPEN")
                }
                binding.tvSaldoPinjaman?.visibility = View.VISIBLE
                binding.edtSaldoPinjaman?.visibility = View.VISIBLE
                binding.tvAdministrasi?.visibility = View.VISIBLE
                binding.edtAdministrasi?.visibility = View.VISIBLE
                binding.tvDanaDiterima?.visibility = View.VISIBLE
                binding.edtDanaDiterima?.visibility = View.VISIBLE
                binding.tvTglPencairan?.visibility = View.VISIBLE
                binding.edtTglPencairan?.visibility = View.VISIBLE
                binding.tvTglBonus?.visibility = View.VISIBLE
                binding.edtTglBonus?.visibility = View.VISIBLE
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}