package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alkindi.gcg_akor.data.remote.response.HitungAdmPinjamanResponse
import com.alkindi.gcg_akor.data.remote.response.TenorListResponse
import com.alkindi.gcg_akor.data.remote.response.TipePotonganResponse
import com.alkindi.gcg_akor.data.remote.retrofit.ApiConfig
import com.alkindi.gcg_akor.data.repository.UserRepository
import com.alkindi.gcg_akor.utils.ApiNetworkingUtils

class PinjamanViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _listPotonganResponse = MutableLiveData<TipePotonganResponse>()
    val listPotonganResponse: LiveData<TipePotonganResponse> = _listPotonganResponse

    private val _listTenor = MutableLiveData<TenorListResponse>()
    val listTenor: LiveData<TenorListResponse> = _listTenor

    private val _hitungAdmResponse = MutableLiveData<HitungAdmPinjamanResponse>()
    val hitungAdmResponse: LiveData<HitungAdmPinjamanResponse> = _hitungAdmResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession() = userRepository.getSession().asLiveData()

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

    suspend fun hitungAdmPinjaman(
        lon: String,
        am: Any,
        term: String,
        mbrid: String,
        sal: Any,
        pot: Any
    ) {
        try {
            _isLoading.value = true

            val apiService = ApiConfig.getApiService()
            var argl = """{
                "lon":"$lon",
                "am":"$am",
                "term":"$term",
                "mbrid":"$mbrid",
                "sal":"$sal",
                "pot":"$pot"
                }""".trimIndent()
            val response = apiService.hitungAdmPinjaman(argl = argl)
            _hitungAdmResponse.value = response
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${Log.ERROR} ")
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun getTenorList(tipePinjaman: String) {
        _isLoading.value = true
        try {
            val apiService = ApiConfig.getApiService()
            val data = mapOf(
                "tipePinjaman" to tipePinjaman
            )
            val encodedData = ApiNetworkingUtils.jsonFormatter(data)
            val fullUrl =
                "${ApiConfig.BASE_URL_KOPEGMAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=tBuYtyWkt9DJpiePfo46tA3TDdHcBBda/t0TnlvsUHGFBHt4quLErXfLmMJrecF6;vars=${encodedData}"
            val response = apiService.getTenorList(fullUrl)
            _listTenor.value = response
        } catch (e: Exception) {
            Log.e(TAG, "An error occured: ${Log.ERROR}")
        } finally {
            _isLoading.value = false
        }
    }

    companion object {
        private val TAG = PinjamanViewModel::class.java.simpleName
    }
}