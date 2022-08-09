package com.example.chatapp.viewmodel

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.model.request.RegisterRequest
import com.example.chatapp.model.response.ErrorResponse
import com.example.chatapp.model.response.LoginResponse
import com.example.chatapp.service.RetrofitFactor
import com.example.chatapp.service.ServiceAppCallApi
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterViewModel {
    private val service: ServiceAppCallApi
    val registerData : MutableLiveData<LoginResponse>
    val errorResponse : MutableLiveData<ErrorResponse>
    val isLoading : ObservableBoolean

    constructor() {
        service = RetrofitFactor.createRetrofit()
        registerData = MutableLiveData()
        errorResponse = MutableLiveData()
        isLoading = ObservableBoolean()
    }

    @SuppressLint("CheckResult")
    fun register(username: String, password: String, avatar: String, email: String, firstName: String, lastName: String) {
        isLoading.set(true)
        service.register(RegisterRequest(username, password, avatar, email, firstName, lastName))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    registerData.value = it
                    isLoading.set(false)
                },
                {
                    if (it is HttpException){
                        //lay data loi tra ve
                        val contentError = it.response()?.errorBody()?.string()
                        val error = Gson().fromJson(contentError, ErrorResponse::class.java)
                        errorResponse.value = error
                    }
                    isLoading.set(false)
                })
    }
}