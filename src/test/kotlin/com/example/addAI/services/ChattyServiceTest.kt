package com.example.addAI.services

import com.example.addAI.Utils.RatesJson
import com.example.addAI.clients.frankfurter.FrankfurterRestClient
import com.example.addAI.utils.Jokes
import com.example.addAI.utils.QuestionsResolver
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ChattyServiceTest {

    private val questionsResolver = QuestionsResolver()
    private val frankfurterMockClient = mock(FrankfurterRestClient::class.java)
    private val conversionService = ConversionService(frankfurterMockClient)
    private val chattyService = ChattyService(questionsResolver, conversionService)

    @Test
    fun getQuestionsTest() {
        val result = chattyService.getQuestions()

        assertTrue(result.contains("Can you tell me a joke?"))
        assertTrue(result.contains("Tell me a joke"))
        assertTrue(result.contains("Convert <amount> <from> to <to>"))
        assertTrue(result.contains("How much is <amount> <from> in <to>"))
    }

    @Test
    fun getAnswerTest() {
        `when`(frankfurterMockClient.getRates()).thenReturn(RatesJson.rates)

        val result1 = chattyService.getAnswer("Convert 10 USD to CZK")
        val result2 = chattyService.getAnswer("How much is 10 USD in CZK")
        val result3 = chattyService.getAnswer("Tell me a joke")
        val result4 = chattyService.getAnswer("Hello")
        val result5 = chattyService.getAnswer("Non sense")

        assertEquals("250.0", result1)
        assertEquals("250.0", result2)
        assertTrue(Jokes.jokes.contains(result3))
        assertEquals("Hello, how can i help you", result4)
        assertEquals("I did not understand", result5)
    }
}