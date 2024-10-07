package com.alkindi.gcg_akor.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DetailSimpananModel(
    val jenisDana: String,
    val nominal: String,
    val tgl: String
) : Parcelable