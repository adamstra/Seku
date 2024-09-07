package com.example.seku.data.network

import com.example.seku.data.model.ChatRequest
import com.example.seku.data.model.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


data class ChatRequest(val question: String)
data class ChatResponse(val response: String)

interface ChatbotApi {
    @POST("/ask")
    fun askQuestion(@Body request: ChatRequest): Call<ChatResponse>
}
