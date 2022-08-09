package com.example.chatapp.ui.base.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.ui.base.fragment.BaseFragment

open class BaseActivity : AppCompatActivity() {
    fun onBackPressRoot() {
        super.onBackPressed()
    }

    fun getCurrentFragment(): BaseFragment? {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment != null && fragment.isVisible && fragment is BaseFragment) {
                return fragment
            }
        }
        return null
    }
    override fun onBackPressed() {
        val fr = getCurrentFragment()
        if (fr!= null) {
            fr.onBackPress()
        }
    }
}