package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.gcg_akor.data.remote.response.RiwayatTransaksiResponse
import com.alkindi.gcg_akor.data.remote.response.TotalPinjamanResponse
import com.alkindi.gcg_akor.data.remote.retrofit.ApiConfig
import com.alkindi.gcg_akor.data.repository.UserRepository
import com.alkindi.gcg_akor.utils.ApiNetworkingUtils

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _totalPinjamanResponse = MutableLiveData<TotalPinjamanResponse>()
    val totalPinjamanResponse: LiveData<TotalPinjamanResponse> = _totalPinjamanResponse

    private val _riwayatTransaksiResponse = MutableLiveData<RiwayatTransaksiResponse>()
    val riwayatTransaksiResponse: LiveData<RiwayatTransaksiResponse> = _riwayatTransaksiResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun checkSavedLoginData() = userRepository.checkSavedPreference()

    fun getSession() = userRepository.getSession().asLiveData()

    suspend fun getTotalPinjaman(userId: String) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "user_id" to userId
            )
            val apiCode = "eDnXtEc2652RTOAUh6gjc0fTS6w6yarnNmGlUghIFK8vvOgHSm1Qlw%3D%3D"
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl =
                "${ApiConfig.BASE_URL_KOPEGMAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=${apiCode};vars=${encodedData}"
            val response = apiService.getTotalPinjamanAmount(fullUrl)
            _totalPinjamanResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to execute the getTotalPinjaman function: ${e.message.toString()}")
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun getRiwayatTransaksi(userId: String) {
        try {
            _isLoading.value = true
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "user_id" to userId
            )
            val apiCode = "eDnXtEc2652RTOAUh6gjczLzlgxjU9l1ptFYPDEmI32mLptn1Yft3g%3D%3D"
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl =
                "${ApiConfig.BASE_URL_KOPEGMAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=${apiCode};vars=${encodedData}"
            val response = apiService.getRiwayatTransaksi(fullUrl)
            _riwayatTransaksiResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Unable to call the function getRiwayatTransaksi: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }
}