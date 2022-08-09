package com.example.chatapp.viewmodel
import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.request.LoginRequest
import com.example.chatapp.model.response.ErrorResponse
import com.example.chatapp.model.response.LoginResponse
import com.example.chatapp.service.RetrofitFactor
import com.example.chatapp.service.ServiceAppCallApi
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

class LoginViewModel : ViewModel {
    private val service: ServiceAppCallApi
    val loginData : MutableLiveData<LoginResponse>
    val errorResponse : MutableLiveData<ErrorResponse>
    val isLoading : ObservableBoolean

    constructor() {
        service = RetrofitFactor.createRetrofit()
        loginData = MutableLiveData()
        errorResponse = MutableLiveData()
        isLoading = ObservableBoolean()
    }

    @SuppressLint("CheckResult")
    fun login(username: String, password: String) {
        isLoading.set(true)
        service.login(
            LoginRequest(username, password)
        )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    loginData.value = it
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