package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.gcg_akor.data.local.model.UserModel
import com.alkindi.gcg_akor.data.remote.response.UserProfileResponseItem
import com.alkindi.gcg_akor.data.remote.retrofit.ApiConfig
import com.alkindi.gcg_akor.data.repository.UserRepository
import com.alkindi.gcg_akor.utils.ApiNetworkingUtils
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _profileDataResponse = MutableLiveData<List<UserProfileResponseItem>>()
    val profileDataResponse: LiveData<List<UserProfileResponseItem>> = _profileDataResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()


    fun getUserProfileData(username: String) {
        if (username.isEmpty()) {
            Log.e(TAG, "Error: ${Log.ERROR}")
        } else {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val apiService = ApiConfig.getApiService()
                    val data = mapOf(
                        "username" to username.uppercase()
                    )
                    val encodedJson = ApiNetworkingUtils.jsonFormatter(data)
//                    val apiCode ="KvRnqbr%2BktvsOVY89qJvEdkJAcJmFdyI7GQ98Ln6DH4%3D"
                    val fullUrl =
                        "${ApiConfig.BASE_URL_KOPEGMAR}txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=YPNRO;rc=KvRnqbr%2BktvsOVY89qJvEdkJAcJmFdyI7GQ98Ln6DH4%3D;vars=${encodedJson}"
                    val response = apiService.getProfile(fullUrl)
                    Log.d(TAG, "Profile Data API Response: $response")
                    Log.d(TAG, "Encoded Json Data: $encodedJson")
                    _profileDataResponse.value = response
                } catch (e: HttpException) {
                    Log.e(TAG, "Error: ${e.message()}")
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }


    fun deleteUserSession(username: String) {
        viewModelScope.launch {
            userRepository.deletePersonalData(username)
            userRepository.logout()
        }
    }


    companion object {
        private val TAG = ProfileViewModel::class.java.simpleName
    }
}