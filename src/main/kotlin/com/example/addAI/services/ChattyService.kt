package com.example.addAI.services

import com.example.addAI.models.MessageTypes
import com.example.addAI.utils.Jokes
import com.example.addAI.utils.Questions
import com.example.addAI.utils.QuestionsResolver
import org.springframework.stereotype.Service


@Service
class ChattyService(
        private val questionsResolver: QuestionsResolver,
        private val conversionService: ConversionService
) {

    fun getQuestions(): Array<String> {
        return Questions.questions
    }

    fun getAnswer(message: String): String {
        val questionType = questionsResolver.resolveQuestionType(message)
        return when (questionType) {
            MessageTypes.GREETING -> "Hello, how can i help you"
            MessageTypes.CONVERSION -> conversionService.convertRate(message)
            MessageTypes.JOKE -> Jokes.jokes.random()
            MessageTypes.UNKNOWN -> "I did not understand"
        }
    }
}