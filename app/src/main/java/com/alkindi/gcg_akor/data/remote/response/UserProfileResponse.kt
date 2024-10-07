package com.alkindi.gcg_akor.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponseItem(
    @SerializedName("fname") val fname: String? = null,
    @SerializedName("telp") val telp: String? = null,
    @SerializedName("email") val email: String? = null
)

