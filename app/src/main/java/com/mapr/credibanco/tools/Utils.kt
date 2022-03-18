package com.mapr.credibanco.tools

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.util.*

class Utils {

    /**
     *
     */
    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }

    /**
     *
     */
    fun convertToBase64(input: String): String {
        return try {
            val data = input.toByteArray(charset("UTF-8"))
            return Base64.encodeToString(data, Base64.NO_WRAP)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Function that prints log in console.
     * @param log String to be printed.
     */
    fun printLog(log: String) {
        Log.e("MSCAPP: ", log)
    }
}