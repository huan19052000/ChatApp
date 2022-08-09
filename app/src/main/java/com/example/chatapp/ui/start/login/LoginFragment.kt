package com.example.chatapp.ui.start.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.chatapp.R
import com.example.chatapp.common.SharedPreferencesCommon
import com.example.chatapp.databinding.FragmentLoginBinding
import com.example.chatapp.ui.base.fragment.BaseFragment
import com.example.chatapp.ui.start.StartActivity
import com.example.chatapp.viewmodel.LoginViewModel


class LoginFragment: BaseFragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener(this)
        binding.signUp.setOnClickListener(this)
        loginViewModel = LoginViewModel()
        registerLiveData()
        binding.data = loginViewModel
        return binding.root
    }


    private fun registerLiveData() {
        //dang ky nhan thong bao khi co su kien thay doi trong loginData
        loginViewModel.loginData.observe(viewLifecycleOwner) {
           // val string = jwtUtils.getAttribute(it.token, "exp")
            SharedPreferencesCommon.saveUserToken(requireContext(), it.token)
            (requireActivity() as StartActivity).openMain()
        }

        loginViewModel.errorResponse.observe(getViewLifecycleOwner(), Observer {
            //roi vao day: mo man hinh Hom
            Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
        })
    }
    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_login-> {
                loginViewModel.login(binding.edtAccount.text.toString(), binding.edtPassword.text.toString())
            }

            R.id.sign_up -> {
                (requireActivity() as StartActivity).openRegisterFragment()
            }
        }
    }
}