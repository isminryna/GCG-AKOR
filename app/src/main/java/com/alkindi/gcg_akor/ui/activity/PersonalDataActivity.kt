package com.alkindi.gcg_akor.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alkindi.gcg_akor.data.model.ViewModelFactory
import com.alkindi.gcg_akor.databinding.ActivityPersonalDataBinding
import com.alkindi.gcg_akor.ui.viewmodel.PersonalDataViewModel
import com.alkindi.gcg_akor.utils.AndroidUIHelper

class PersonalDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalDataBinding
    private lateinit var idMember: String
    private val personalDataViewModel: PersonalDataViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showData()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showData() {
        val male = "Laki-laki"
        val female = "Perempuan"
        personalDataViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        getData()
        personalDataViewModel.savedPersonalData.observe(this) { res ->
            val personalData =if (!res.isNullOrEmpty()) res[0] else null

            binding.edtNama.setText(personalData?.nama ?: "Kosong")
            binding.edtNIP.setText(personalData?.mbrEmpno ?: "Kosong")
            binding.edtIdMember.setText(idMember)
            binding.edtTempatLahir.setText(personalData?.tempatLahir ?: "Kosong")
            binding.edtTglLahir.setText(personalData?.tglLahir ?: "Kosong")
            binding.edtKelamin.setText(
                if (personalData?.jenisKelamin == "M") male else female
            )
            binding.edtAlamat.setText(personalData?.alamat ?: "Kosong")
            binding.edtNoHP.setText(personalData?.noHp ?: "Kosong")
            binding.edtEmail.setText(personalData?.email ?: "Kosong")

            // Show AlertDialog if data is null or empty
            if (personalData == null) {
                AndroidUIHelper.showAlertDialog(this, "Error", "Gagal Mengambil data dari API")
            }
//            if (!res.isNullOrEmpty()) {
//                binding.edtNama.setText(res[0].nama ?:"Kosong")
//                binding.edtNIP.setText(res[0].mbrEmpno ?: "Kosong")
//                binding.edtIdMember.setText(idMember)
//                binding.edtTempatLahir.setText(res[0].tempatLahir ?: "Kosong")
//                binding.edtTglLahir.setText(res[0].tglLahir ?: "Kosong")
//                if (res[0].jenisKelamin == "M")
//                    binding.edtKelamin.setText(male)
//                else
//                    binding.edtKelamin.setText(female)
//                binding.edtAlamat.setText(res[0].alamat ?: "Kosong")
//                binding.edtNoHP.setText(res[0].noHp ?: "Kosong")
//                binding.edtEmail.setText(res[0].email ?: "Kosong")
//            } else {
//                AndroidUIHelper.showAlertDialog(this, "Error", "Gagal Mengambil data dari API")
//                binding.edtNama.setText("Kosong")
//                binding.edtNIP.setText("Kosong")
//                binding.edtIdMember.setText(idMember)
//                binding.edtTempatLahir.setText( "Kosong")
//                binding.edtTglLahir.setText("Kosong")
//                if (res[0].jenisKelamin == "M")
//                    binding.edtKelamin.setText(male)
//                else
//                    binding.edtKelamin.setText(female)
//                binding.edtAlamat.setText("Kosong")
//                binding.edtNoHP.setText( "Kosong")
//                binding.edtEmail.setText("Kosong")
//            }
        }
    }

    private fun getData() {
        personalDataViewModel.getSession().observe(this) {
            personalDataViewModel.getPersonalData(it.username)
            idMember = it.username
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading)
            binding.progressBar.visibility = View.VISIBLE else
            binding.progressBar.visibility = View.GONE
    }
}