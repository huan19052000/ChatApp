package com.example.chatapp.common

import android.content.Context

object SharedPreferencesCommon {
    fun saveUserToken(context: Context, token: String) {
        val share = context.getSharedPreferences("user account", Context.MODE_PRIVATE)
        val edt = share.edit()
        edt.putString("TOKEN", token)
        edt.apply()
    }

    fun readUserToken(context: Context): String {
        val token = context.getSharedPreferences("user account", Context.MODE_PRIVATE)
            .getString("TOKEN", "")
        if (token == null) {
            return ""
        }
        return token
    }
}