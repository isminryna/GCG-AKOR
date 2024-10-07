package com.alkindi.gcg_akor.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.alkindi.gcg_akor.data.repository.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun checkSavedLoginData() = userRepository.checkSavedPreference()
}