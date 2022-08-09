package com.example.chatapp.common

import android.app.Application
import android.content.Context

class CommonApp : Application() {
    companion object {
        var userId = -1
        var avatar = ""
        private var context: Context? = null
        fun getContextApp(): Context {
            return context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}