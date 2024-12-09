package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.gcg_akor.data.remote.response.DetailSimpananResponse
import com.alkindi.gcg_akor.data.remote.retrofit.ApiConfig
import com.alkindi.gcg_akor.data.repository.UserRepository
import com.alkindi.gcg_akor.utils.ApiNetworkingUtils
import kotlinx.coroutines.launch

class DetailSimpananFragmentViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _detailSimpananResponse = MutableLiveData<DetailSimpananResponse>()
    val detailSimpananResponse: LiveData<DetailSimpananResponse> = _detailSimpananResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession() = userRepository.getSession().asLiveData()

    fun getDetailSimpananData(userID: String, simpType: String) {
        if (userID.isEmpty() && simpType.isEmpty()) {
            Log.e(TAG, "Error: ${Log.ERROR}")
        } else {
            _isLoading.value = true
            try {
                viewModelScope.launch {
                    val apiService = ApiConfig.getApiService()
                    val data = mapOf(
                        "userID" to userID.uppercase(),
                        "tipeSimp" to simpType
                    )
                    val encodedData = ApiNetworkingUtils.jsonFormatter(data)
                    val apiCode = "NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6yTAxuvzoHj/mdTherKRvxb70mprxQNT2w"
                    val fullUrl =
                        "${ApiConfig.BASE_URL_KOPEGMAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=${apiCode};vars=${encodedData}"
                    val response = apiService.getDetailSimpanan(fullUrl)
                    _detailSimpananResponse.value = response
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        private val TAG = DetailSimpananFragmentViewModel::class.java.simpleName
    }
}