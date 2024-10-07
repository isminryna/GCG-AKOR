package com.alkindi.gcg_akor.data.local.di

import android.content.Context
import com.alkindi.gcg_akor.data.local.db.AkorDB
import com.alkindi.gcg_akor.data.local.pref.UserPreference
import com.alkindi.gcg_akor.data.local.pref.datastore
import com.alkindi.gcg_akor.data.remote.retrofit.ApiConfig
import com.alkindi.gcg_akor.data.repository.UserRepository
import com.alkindi.gcg_akor.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.datastore)
        val apiService = ApiConfig.getApiService()
        val db = AkorDB.getDB(context)
        val dao = db.userPersonalDAO()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(apiService, pref, dao)
    }
}