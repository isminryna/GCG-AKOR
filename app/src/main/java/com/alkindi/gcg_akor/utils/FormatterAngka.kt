package com.alkindi.gcg_akor.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

object FormatterAngka {

    fun formatterAngkaRibuan(angka: Int): String {
        val formattedNilai = NumberFormat.getInstance(Locale("ind", "ID"))
        return formattedNilai.format(angka)
    }

    fun formatterRibuanKeInt(angka: String): Int {
        return angka.replace(".", "").replace(" ", "").replace(",", "").toInt()
    }

    fun formatterAngkaRibuanDouble(angka: Double?): String {
        val formattedNilai = NumberFormat.getInstance(Locale("ind", "ID"))
        return formattedNilai.format(angka)
    }

    fun formatterRibuanKeIntDouble(angka: String): Double {
        return angka.replace(".", "").replace(" ", "").toDouble()
    }

    fun formatterAngkaRibuanUntukDisplay(angka: Any): String {
        val formattedNilai = NumberFormat.getInstance(Locale("ind", "ID"))
        return formattedNilai.format(angka)
    }

    fun dateFormatForDetail(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

        val date = inputFormat.parse(dateString)
        return outputFormat.format(date ?: "")
    }

}