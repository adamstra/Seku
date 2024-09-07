package com.example.seku.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seku.data.model.ChatRequest
import com.example.seku.data.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

data class Message(val text: String, val isUser: Boolean)

class ChatViewModel : ViewModel() {
    var messages by mutableStateOf(listOf<Message>())
    var errorMessage by mutableStateOf<String?>(null)

    fun askChatbot(question: String) {
        viewModelScope.launch {
            try {
                // Add the user's question to the message list
                messages = messages + Message(question, true)

                val response = RetrofitClient.api.askQuestion(ChatRequest(question)).awaitResponse()

                if (response.isSuccessful) {
                    // Add the chatbot's response to the message list
                    val botResponse = response.body()?.response ?: "Erreur inconnue"
                    messages = messages + Message(botResponse, false)
                } else {
                    errorMessage = "Erreur lors de la demande: ${response.code()}"
                    Log.e("ChatViewModel", "Error response: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                errorMessage = "Erreur: ${e.message}"
                Log.e("ChatViewModel", "Exception occurred: ", e)
            }
        }
    }

    fun clearError() {
        errorMessage = null
    }
}


