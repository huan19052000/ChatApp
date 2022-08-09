package com.example.chatapp.ui.start.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.R
import com.example.chatapp.ui.base.fragment.BaseFragment
import android.os.Handler
import com.example.chatapp.common.SharedPreferencesCommon
import com.example.chatapp.ui.start.StartActivity


class SplashFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val token = SharedPreferencesCommon.readUserToken(requireContext())
        Handler().postDelayed({
            if (token == "") {
                (requireActivity() as StartActivity).openLoginFragment()
            }
            else {
                (requireActivity() as StartActivity).openMain()
            }
        }, 3000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}