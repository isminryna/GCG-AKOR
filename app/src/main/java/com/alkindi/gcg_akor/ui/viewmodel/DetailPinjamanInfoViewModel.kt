package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkindi.gcg_akor.data.remote.response.DetailHistoryPinjamanResponse
import com.alkindi.gcg_akor.data.repository.UserRepository

class DetailPinjamanInfoViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _detailPinjamanInfoResponse = MutableLiveData<DetailHistoryPinjamanResponse>()
    val detailHistoryPinjamanInfoResponse: LiveData<DetailHistoryPinjamanResponse> =
        _detailPinjamanInfoResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailPinjamanInfo(docNum: String) {
        if (docNum.isEmpty()) {
            Log.e(TAG, "Tidak ada nilai yang dikirim pada parameter")
            return
        } else {
//TODO: Kerjain detail pinjaman user
        }
    }

    companion object {
        private val TAG = DetailPinjamanInfoViewModel::class.java.simpleName
    }
}