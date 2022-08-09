package com.example.chatapp.service

import com.example.chatapp.model.request.LoginRequest
import com.example.chatapp.model.request.RegisterRequest
import com.example.chatapp.model.response.FriendResponse
import com.example.chatapp.model.response.LoginResponse
import com.example.chatapp.model.response.MessageChatResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceAppCallApi {

    @POST(value = "/users/login")
    fun login(
        @Body body: LoginRequest
    ): Observable<LoginResponse>

    @POST(value = "/users/register")
    fun register(
        @Body body: RegisterRequest
    ): Observable<LoginResponse>

    @GET(value = "/api/friends")
    fun getFriends(
        @Query("status") status: String
    ): Observable<MutableList<FriendResponse>>

    @GET("/api/messages")
    fun getMessage(
        @Query("friendId") friendId:Int
    ):Observable<MutableList<MessageChatResponse>>
}