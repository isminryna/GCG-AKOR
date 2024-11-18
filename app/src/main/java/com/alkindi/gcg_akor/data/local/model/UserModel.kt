package com.alkindi.gcg_akor.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserModel(
    val username: String,
    val password: String,
    val isLogin: Boolean
)

@Parcelize
data class SimpTypeWNominal(
    val tipeSimpanan: String,
    val nominal: String
) : Parcelable

@Parcelize
data class PinjDocnum(
    val docNum: String
) : Parcelable

@Parcelize
data class ProcessedTarikSimp(
    val tipeSimpanan: String?,
    val nominal: String?,
    val tglTransaksi: String?,
    val docnum: String?
) : Parcelable

@Parcelize
data class UserProfile(
    val namaUser: String?,
    val emailUser: String?,
    val noHPUser: String?
) : Parcelable