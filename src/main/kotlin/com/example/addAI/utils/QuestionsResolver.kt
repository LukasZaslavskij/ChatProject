package com.example.addAI.utils

import com.example.addAI.models.MessageTypes
import org.springframework.stereotype.Component

@Component
class QuestionsResolver {

    fun resolveQuestionType(message: String): MessageTypes {
        return when {
            message.contains("hi", ignoreCase = true) ||
                    message.contains("hello", ignoreCase = true) -> MessageTypes.GREETING
            message.contains("joke") -> MessageTypes.JOKE
            message.contains("How much") || message.contains("Convert") -> MessageTypes.CONVERSION
            else -> MessageTypes.UNKNOWN
        }
    }
}