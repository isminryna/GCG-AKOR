package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.gcg_akor.data.local.model.UserModel
import com.alkindi.gcg_akor.data.remote.response.UpdateProfileResponse
import com.alkindi.gcg_akor.data.remote.retrofit.ApiConfig
import com.alkindi.gcg_akor.data.repository.UserRepository
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
    val updateProfileResponse: LiveData<UpdateProfileResponse> = _updateProfileResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()


    fun updateUserProfile(
        userId: String,
        namaUser: String?,
        noHP: String?,
        emailUser: String?,
        pwLama: String?,
        pwBaru: String?,
        pwKetikUlang: String?
    ) {
        try {
            val argl = """{
                    "userID":"$userId",
                    "userName":"${namaUser ?:""}",
                    "phoneNumber":"${noHP ?:""}",
                    "emailAddress":"${emailUser?:""}",
                    "cp":"${pwLama?:""}",
                    "np":"${pwBaru?:""}",
                    "rp":"${pwKetikUlang?:""}"
                    }""".trimIndent()
            Log.d(TAG, "Nilai yang ingin diubah: $argl")
            _isLoading.value = true
            viewModelScope.launch {
                val apiService = ApiConfig.getApiService()
                val response = apiService.updateProfileData(argl = argl)
                _updateProfileResponse.value = response
            }
        } catch (e: Exception) {
            Log.e(TAG, "Update user profile data failed: ${Log.ERROR}")
        } finally {
            _isLoading.value = false
        }
    }

    companion object {
        private val TAG = EditProfileViewModel::class.java.simpleName
    }
}