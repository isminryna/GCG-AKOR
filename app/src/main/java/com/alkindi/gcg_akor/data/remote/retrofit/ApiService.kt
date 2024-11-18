package com.alkindi.gcg_akor.data.remote.retrofit

import com.alkindi.gcg_akor.data.remote.response.DetailHistoryPinjamanResponse
import com.alkindi.gcg_akor.data.remote.response.DetailSimpananResponse
import com.alkindi.gcg_akor.data.remote.response.ExtUserProfileResponse
import com.alkindi.gcg_akor.data.remote.response.HistoryPinjamanResponse
import com.alkindi.gcg_akor.data.remote.response.HistoryTarikSimpResponse
import com.alkindi.gcg_akor.data.remote.response.LoginResponse
import com.alkindi.gcg_akor.data.remote.response.NominalSimpananResponse
import com.alkindi.gcg_akor.data.remote.response.PersonalDataResponse
import com.alkindi.gcg_akor.data.remote.response.TarikNominalSimpananResponse
import com.alkindi.gcg_akor.data.remote.response.TipePotonganResponse
import com.alkindi.gcg_akor.data.remote.response.UpdateProfileResponse
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

    @GET
    suspend fun getProfileExt(  // New getProfile API endpoint
        @Url fullUrl: String = ApiConfig.BASE_URL_KOPEGMAR
    ): ExtUserProfileResponse

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

    @GET
    suspend fun getDetailSimpanan(
        @Url fullUrl: String
    ): DetailSimpananResponse

    @GET
    suspend fun getDetailHistoryPinjaman(
        @Url fullUrl: String
    ): DetailHistoryPinjamanResponse

    @FormUrlEncoded
    @POST("https://kopegmar.gcgakor.id/txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=NU5mgOhAZUGhJ24WH1zuqwTnRtBFfK6y6OVw0Q2/ZWSE2T%2BDBSLsen/SgBttLGZS")
    suspend fun postTarikSimpanan(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): TarikNominalSimpananResponse

    @FormUrlEncoded
    @POST("https://kopegmar.gcgakor.id/txn?fnc=runLib;opic=${ApiConfig.API_DEV_CODE_KOPEGMAR};csn=${ApiConfig.WORKSPACE_CODE_KOPEGMAR};rc=KvRnqbr%2Bktu7HRDvQttp6MRyn3VICeItrNiEgAa5Ce0%3D")
    suspend fun updateProfileData(
        @Field("argt") argt: String = "vars",
        @Field("argl") argl: String
    ): UpdateProfileResponse
}