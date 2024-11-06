package com.alkindi.gcg_akor.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserModel(
    val username: String,
    val password: String,
    val isLogin: Boolean
)

@Parcelize
data class simpTypeWNominal(
    val tipeSimpanan: String,
    val nominal: String
):Parcelable