package com.example.chatapp.model.request

data class RegisterRequest (val username: String, val password: String, val avatar: String, val email: String
                            , val firstName: String, val lastName: String)