package com.example.chatapp.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.common.CommonApp
import com.example.chatapp.common.CommonDataBinding
import com.example.chatapp.databinding.FragmentSetttingsBinding
import com.example.chatapp.ui.base.fragment.BaseFragment

class SettingFragment: BaseFragment() {
    private lateinit var binding: FragmentSetttingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetttingsBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener {
            onBackPress()
        }
        binding.changeAccount.setOnClickListener{

        }
        CommonDataBinding.loadNormalImageLink(binding.avatar, CommonApp.avatar)
        return binding.root
    }
}