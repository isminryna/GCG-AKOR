package com.alkindi.gcg_akor.data.remote.retrofit

import com.alkindi.gcg_akor.data.remote.response.LoginResponse
import com.alkindi.gcg_akor.data.remote.response.PersonalDataResponse
import com.alkindi.gcg_akor.data.remote.response.UserProfileResponseItem
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @FormUrlEncoded
    @POST("preodm?fnc=auth")
    suspend fun login(
        @Field("csn") workSpace: String,
        @Field("usn") username: String,
        @Field("pwd") password: String,
        @Field("unifie") unifie: String,
        @Field("urifie") urifie: String
    ): LoginResponse


    //    @GET("txn?fnc=runLib;opic=79ipFwZR44YmvtrR1M1t9A;csn=YPNRO;rc=KvRnqbr%2BktvsOVY89qJvEdkJAcJmFdyI7GQ98Ln6DH4%3D;")
    @GET
    suspend fun getProfile(
//        @Query("vars") username: String
        @Url fullUrl: String
    ): List<UserProfileResponseItem>

    @GET
    suspend fun getPersonal(
        @Url fullUrl: String
    ): List<PersonalDataResponse>
}