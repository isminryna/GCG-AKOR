package com.alkindi.gcg_akor.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alkindi.gcg_akor.data.local.db.room.entity.PersonalDataEntity
import com.alkindi.gcg_akor.data.local.model.UserModel
import com.alkindi.gcg_akor.data.remote.response.PersonalDataResponse
import com.alkindi.gcg_akor.data.repository.UserRepository
import kotlinx.coroutines.launch

class PersonalDataViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _personalDataResponse = MutableLiveData<List<PersonalDataResponse>?>()
    val personalDataResponse: LiveData<List<PersonalDataResponse>?> = _personalDataResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> = userRepository.getSession().asLiveData()

    val savedPersonalData: LiveData<List<PersonalDataEntity>> = userRepository.getUserPersonalData()


    fun getPersonalData(username: String?) {
        if (username == null) Log.e(
            TAG, "Couldn't fetch current user data: " + Log.ERROR.toString()
        ) else {
            try {
                viewModelScope.launch {
                    _isLoading.value = true
                    userRepository.fetchUserPersonalData(username)
                    _isLoading.value = false
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error in Viewmodel class: ${e.message.toString()}")
                _isLoading.value = false
            }
        }
    }


    companion object {
        private val TAG = PersonalDataViewModel::class.java.simpleName
    }
}