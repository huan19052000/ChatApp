package com.example.chatapp.ui.base.fragment

import androidx.fragment.app.Fragment
import com.example.chatapp.ui.base.activity.BaseActivity

open abstract class BaseFragment: Fragment() {
    fun onBackPress(){
        (requireActivity() as BaseActivity).onBackPressRoot()
    }
}