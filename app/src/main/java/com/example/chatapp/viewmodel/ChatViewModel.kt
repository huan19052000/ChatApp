package com.example.chatapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.common.CommonApp
import com.example.chatapp.common.SharedPreferencesCommon
import com.example.chatapp.model.response.ErrorResponse
import com.example.chatapp.model.response.MessageChatResponse
import com.example.chatapp.service.RetrofitFactor
import com.example.chatapp.service.ServiceAppCallApi
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

class ChatViewModel : ViewModel {
    private val service: ServiceAppCallApi
    private val messagesModel: MutableLiveData<MutableList<MessageChatResponse>>
    val errorResponse: MutableLiveData<ErrorResponse>
    val imageResponse: MutableLiveData<String>
    val isLoading: MutableLiveData<Boolean>

    constructor() {
        service = RetrofitFactor.createRetrofitToken(
            SharedPreferencesCommon.readUserToken(CommonApp.getContextApp())
        )
        this.messagesModel = MutableLiveData()
        errorResponse = MutableLiveData()
        imageResponse = MutableLiveData()
        isLoading = MutableLiveData()
    }

    fun getMessagesModel(): MutableLiveData<MutableList<MessageChatResponse>> {
        return messagesModel
    }

    @SuppressLint("CheckResult")
    fun getMessage(friendId: Int) {
        service.getMessage(
            friendId
        )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    messagesModel.value = it
                    isLoading.value = false
                },
                {
                    if (it is HttpException) {
                        val contentError = (it as HttpException).response().errorBody()?.string()
                        val error =
                            Gson().fromJson<ErrorResponse>(contentError, ErrorResponse::class.java)
                        errorResponse.value = error
                    }
                    isLoading.value = false
                })
    }

//    fun sendImage(path:String){
//        val file = File(path)
//        val requestFile =  RequestBody.create(
//            "multipart/form-data".toMediaType(),
//            file
//        )
//        val part = MultipartBody.Part.createFormData(
//            "imageFile", file.getName(),
//            requestFile)
//        service.postImage(
//            part
//        )
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    imageResponse.value=it
//                    isLoading.value = false
//                },
//                {
//                    if (it is HttpException) {
//                        val contentError = (it as HttpException).response().errorBody()?.string()
//                        val error =
//                            Gson().fromJson<ErrorResponse>(contentError, ErrorResponse::class.java)
//                        errorResponse.value = error
//                    }
//                    isLoading.value = false
//                })
//    }
}