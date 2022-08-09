package com.example.chatapp.viewmodel

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.common.CommonApp
import com.example.chatapp.common.SharedPreferencesCommon
import com.example.chatapp.model.response.ErrorResponse
import com.example.chatapp.model.response.FriendResponse
import com.example.chatapp.service.RetrofitFactor
import com.example.chatapp.service.ServiceAppCallApi
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FriendViewModel : ViewModel {
    private val service: ServiceAppCallApi
    val friendsModel: MutableLiveData<MutableList<FriendResponse>>
    val errorResponse: MutableLiveData<ErrorResponse>
    val isLoading: MutableLiveData<Boolean>

    constructor() {
        service = RetrofitFactor.createRetrofitToken(
            SharedPreferencesCommon.readUserToken(CommonApp.getContextApp())
        )
        friendsModel = MutableLiveData()
        errorResponse = MutableLiveData()
        isLoading = MutableLiveData()
    }

    @SuppressLint("CheckResult")
    fun getFriends() {
        isLoading.value = true
            service.getFriends("accepted")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        friendsModel.value = it
                        isLoading.value = false
                    },
                    {
                        if (it is HttpException){
                            //lay data loi tra ve
                            val contentError = it.response()?.errorBody()?.string()
                            val error = Gson().fromJson(contentError, ErrorResponse::class.java)
                            errorResponse.value = error
                        }
                        isLoading.value = false
                    })
    }
}