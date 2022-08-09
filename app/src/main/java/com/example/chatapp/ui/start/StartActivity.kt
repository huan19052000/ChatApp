package com.example.chatapp.ui.start

import android.content.Intent
import android.os.Bundle
import com.example.chatapp.R
import com.example.chatapp.common.CommonApp
import com.example.chatapp.common.SharedPreferencesCommon
import com.example.chatapp.socket.SocketManager
import com.example.chatapp.ui.base.activity.BaseActivity
import com.example.chatapp.ui.main.MainActivity
import com.example.chatapp.ui.start.login.LoginFragment
import com.example.chatapp.ui.start.register.RegisterFragment
import com.example.chatapp.ui.start.splash.SplashFragment
import com.example.chatapp.utils.JwtUtils

class StartActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val bundle = intent.extras
        if(bundle?.getString("TYPE") != null && bundle.getString("TYPE").equals("OPEN_LOGIN")) {
            openLoginFragment()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.content, SplashFragment(), SplashFragment::class.java.name)
                .commit()
        }
    }

    fun openLoginFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.open_to_left, R.anim.exit_to_left,
                R.anim.open_to_right, R.anim.exit_to_right)
            .replace(R.id.content, LoginFragment(), LoginFragment::class.java.name)
            .commit()
    }

    fun openRegisterFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.open_to_left, R.anim.exit_to_left,
                R.anim.open_to_right, R.anim.exit_to_right)
            .replace(R.id.content, RegisterFragment(), RegisterFragment::class.java.name)
            .commit()
    }


    fun openMain() {
        val token = SharedPreferencesCommon.readUserToken(this)
        val userId = JwtUtils.getAttribute(token,"jti")!!.toInt()
        val avatar = JwtUtils.getAttribute(token,"avatar")
        CommonApp.userId = userId
        CommonApp.avatar = avatar!!
        SocketManager.getInstance().connect()
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}