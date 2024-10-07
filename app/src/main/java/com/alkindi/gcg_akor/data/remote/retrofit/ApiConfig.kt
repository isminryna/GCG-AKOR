package com.alkindi.gcg_akor.data.remote.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    const val BASE_URL_KOPEGMAR = "https://kopegmar.gcgakor.id/"
    const val API_DEV_CODE_KOPEGMAR = "iJRj9e2v1xIL1ib2xkM4A"
    const val WORKSPACE_CODE_KOPEGMAR = "YPNRO"
    fun getApiService(): ApiService {

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_KOPEGMAR)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}