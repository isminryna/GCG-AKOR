package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.gcg_akor.data.local.model.UserModel
import com.alkindi.gcg_akor.data.remote.response.HistoryTarikSimpResponse
import com.alkindi.gcg_akor.data.remote.retrofit.ApiConfig
import com.alkindi.gcg_akor.data.repository.UserRepository
import com.alkindi.gcg_akor.utils.ApiNetworkingUtils

class RiwayatTransaksiFragmentViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _historyTarikSimpananResponse = MutableLiveData<List<HistoryTarikSimpResponse>>()
    val historyTarikSimpResponse: LiveData<List<HistoryTarikSimpResponse>> =
        _historyTarikSimpananResponse
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()


    suspend fun getHistoryTarikSimp(username: String) {
        if (username.isEmpty()) {
            Log.e(TAG, "Couldn't get the current user ID: ${Log.ERROR}")
        } else {
            _isLoading.value = true
            try {
                val data = mapOf(
                    "username" to username.uppercase()
                )
                val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                val apiCode = "NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6yMhf%2B8XnZd799HXV35pDBeAmaQOPnZORD"
//            val fullUrl =
//                "${ApiConfig.BASE_URL_KOPEGMAR}txn?fmc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=${apiCode};vars=${encodedData}"
                val fullUrl =
                    "${ApiConfig.BASE_URL_KOPEGMAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=${apiCode};vars=${encodedData}"
                val apiService = ApiConfig.getApiService()
                val responds = apiService.getHistorySimpanan(fullUrl)
                _historyTarikSimpananResponse.value = responds
                Log.d(TAG, "API Response: $responds")
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching the data: ${e.message.toString()}")
            } finally {
                _isLoading.value = false
            }

        }
    }

    companion object {
        private val TAG = TarikSimpananViewModel::class.java.simpleName
    }
}