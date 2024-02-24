package com.example.addAI.api.controller

import com.example.addAI.api.model.MessageRequest
import com.example.addAI.services.ChattyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChattyController(
        private val chattyService: ChattyService
) {

    @GetMapping("/questions")
    fun getQuestions(): Array<String> {
        return chattyService.getQuestions()
    }

    @PostMapping("/conversation")
    fun receiveMessage(@RequestBody messageRequest: MessageRequest): String {
        return chattyService.getAnswer(messageRequest.message)
    }
}