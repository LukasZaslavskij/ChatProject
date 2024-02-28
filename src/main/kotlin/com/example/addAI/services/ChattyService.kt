package com.example.addAI.services

import com.example.addAI.models.MessageTypes
import com.example.addAI.repository.DatabaseData
import org.springframework.stereotype.Service


@Service
class ChattyService(
        private val conversionService: ConversionService,
        private val databaseData: DatabaseData
) {

    fun getQuestions(): Array<String> {
        return databaseData.questions()
    }

    fun getAnswer(message: String): String {
        val questionType = resolveQuestionType(message)
        return when (questionType) {
            MessageTypes.GREETING -> "Hello, how can i help you"
            MessageTypes.CONVERSION -> conversionService.convertRate(message)
            MessageTypes.JOKE -> databaseData.jokes().random()
            MessageTypes.UNKNOWN -> "I did not understand"
        }
    }

    private fun resolveQuestionType(message: String): MessageTypes {
        return when {
            message.contains("hi", ignoreCase = true) ||
                    message.contains("hello", ignoreCase = true) -> MessageTypes.GREETING
            message.contains("joke") -> MessageTypes.JOKE
            message.contains("How much") || message.contains("Convert") -> MessageTypes.CONVERSION
            else -> MessageTypes.UNKNOWN
        }
    }
}