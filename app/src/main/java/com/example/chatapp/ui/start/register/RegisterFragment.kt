package com.example.chatapp.ui.start.register

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.chatapp.R
import com.example.chatapp.common.SharedPreferencesCommon
import com.example.chatapp.databinding.FragmentRegisterBinding
import com.example.chatapp.ui.base.fragment.BaseFragment
import com.example.chatapp.ui.start.StartActivity
import com.example.chatapp.viewmodel.LoginViewModel
import com.example.chatapp.viewmodel.RegisterViewModel

class RegisterFragment : BaseFragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dialog : Dialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btnRegister.setOnClickListener(this)
        registerViewModel = RegisterViewModel()
        loginViewModel = LoginViewModel()
        registerLiveData()
        return binding.root
    }


    private fun registerLiveData() {
        //dang ky nhan thong bao khi co su kien thay doi trong loginData
        registerViewModel.registerData.observe(viewLifecycleOwner) {
            Handler().postDelayed({
                SharedPreferencesCommon.saveUserToken(requireContext(), it.token)
                openDialog()
            }, 3000)
        }

        registerViewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            //roi vao day: mo man hinh Hom
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun openDialog() {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.register_notification)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        var buttonOk: Button = dialog.findViewById(R.id.btn_ok)
        buttonOk.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_register -> {
                if (binding.edtPassword.text.toString().equals(binding.confirmPassword.text.toString())) {
                    registerViewModel.register(
                        binding.edtUsername.text.toString(),
                        binding.edtPassword.text.toString(),
                        binding.avatar.text.toString(),
                        binding.email.text.toString(),
                        binding.firstName.text.toString(),
                        binding.lastName.text.toString()
                    )
                    binding.tvNotify.visibility = View.GONE
                } else {
                    binding.tvNotify.visibility = View.VISIBLE
                }
            }

            R.id.btn_ok-> {
                (requireActivity() as StartActivity).openMain()
            }
        }
    }
}