package com.example.chatapp.model.response

import java.util.*

class MessageChatResponse {
    var id = ""
    var senderId = 0
    var receiverId = 0
    var content: String = ""
    var type = "text"
    var createdAt: Date? = null
    var updatedAt:Date? = null
}