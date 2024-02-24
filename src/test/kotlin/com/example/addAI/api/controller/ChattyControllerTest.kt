package com.example.addAI.api.controller

import com.example.addAI.api.model.MessageRequest
import com.example.addAI.services.ChattyService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(ChattyController::class)
@AutoConfigureMockMvc
class ChattyControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var chattyService: ChattyService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun getQuestionsTest() {
        val questions = arrayOf("Question 1", "Question 2", "Question 3")
        `when`(chattyService.getQuestions()).thenReturn(questions)

        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$").isArray)
                .andExpect(jsonPath("\$[0]").value("Question 1"))
                .andExpect(jsonPath("\$[1]").value("Question 2"))
                .andExpect(jsonPath("\$[2]").value("Question 3"))
    }

    @Test
    fun receiveMessageTest() {
        val messageRequest = MessageRequest("Hello")
        `when`(chattyService.getAnswer(anyString())).thenReturn("Hi, how can I help you?")

        mockMvc.perform(post("/conversation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isOk)
                .andExpect(content().string("Hi, how can I help you?"))
    }
}