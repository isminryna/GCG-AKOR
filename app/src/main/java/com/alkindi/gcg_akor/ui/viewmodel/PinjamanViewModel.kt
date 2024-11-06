package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkindi.gcg_akor.data.remote.response.TipePotonganResponse
import com.alkindi.gcg_akor.data.remote.retrofit.ApiConfig
import com.alkindi.gcg_akor.data.repository.UserRepository
import com.alkindi.gcg_akor.utils.ApiNetworkingUtils

class PinjamanViewModel(userRepository: UserRepository) : ViewModel() {
    private val _listPotonganResponse = MutableLiveData<TipePotonganResponse>()
    val listPotonganResponse: LiveData<TipePotonganResponse> = _listPotonganResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getTipePotonganList(tipePinjaman: String) {
        if (tipePinjaman.isEmpty()) {
            Log.e(TAG, "Parameter 'tipePinjaman' isn't receive any value: ${Log.ERROR}")
        } else {
            try {
                _isLoading.value = true
                val apiService = ApiConfig.getApiService()
                val data = mapOf(
                    "tipepotongan" to tipePinjaman
                )
                val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                val apiCode = "tBuYtyWkt9BVca9T0hdoheGNqUKSfmxc0OeVrYJV618%3D"
                val fullUrl =
                    "${ApiConfig.BASE_URL_KOPEGMAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=${apiCode};vars=${encodedData}"
                val response = apiService.getTipePotonganUser(fullUrl)
                _listPotonganResponse.value = response
            } catch (e: Exception) {
                Log.e(TAG, "Failed to call the API ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        private val TAG = PinjamanViewModel::class.java.simpleName
    }
}