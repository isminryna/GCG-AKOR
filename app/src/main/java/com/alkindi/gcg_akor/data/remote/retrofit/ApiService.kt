package com.alkindi.gcg_akor.data.remote.retrofit

import com.alkindi.gcg_akor.data.remote.response.HistoryPinjamanResponse
import com.alkindi.gcg_akor.data.remote.response.HistoryTarikSimpResponse
import com.alkindi.gcg_akor.data.remote.response.LoginResponse
import com.alkindi.gcg_akor.data.remote.response.NominalSimpananResponse
import com.alkindi.gcg_akor.data.remote.response.PersonalDataResponse
import com.alkindi.gcg_akor.data.remote.response.TarikNominalSimpananResponse
import com.alkindi.gcg_akor.data.remote.response.TipePotonganResponse
import com.alkindi.gcg_akor.data.remote.response.UserProfileResponseItem
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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
        @Url fullUrl: String = ApiConfig.BASE_URL_KOPEGMAR
    ): List<UserProfileResponseItem>

    @GET
    suspend fun getPersonal(
        @Url fullUrl: String
    ): List<PersonalDataResponse>

    @GET
    suspend fun getHistorySimpanan(
        @Url fullUrl: String
    ): List<HistoryTarikSimpResponse>

    @GET
    suspend fun getNominalSimpananUser(
        @Url fullUrl: String
    ): NominalSimpananResponse

    @GET
    suspend fun getTipePotonganUser(
        @Url fullUrl: String
    ): TipePotonganResponse

    @GET
    suspend fun getHistoryPinjaman(
        @Url fullUrl: String
    ): HistoryPinjamanResponse

    @FormUrlEncoded
    @POST("https://kopegmar.gcgakor.id/txn?fnc=runLib;opic=iJRj9e2v1xIL1ib2xkM4A;csn=YPNRO;rc=NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6y6OVw0Q2/ZWSE2T%2BDBSLsen/SgBttLGZS")
    suspend fun postTarikSimpanan(
//        @Url fullUrl: String,
        @Field("argt") argt: String ="vars",
        @Field("argl") argl: String
    ): TarikNominalSimpananResponse
}