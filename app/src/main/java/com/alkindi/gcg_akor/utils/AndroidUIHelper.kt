package com.alkindi.gcg_akor.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

object AndroidUIHelper {

    fun showWarningToastShort(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showAlertDialog(context: Context, title: String, msg: String) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(msg)
            setPositiveButton("Ok") { _, _ ->
            }
            create()
            show()
        }
    }
}