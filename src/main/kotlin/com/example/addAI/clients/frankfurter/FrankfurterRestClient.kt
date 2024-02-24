package com.example.addAI.clients.frankfurter

import com.example.addAI.utils.Logger
import lombok.extern.slf4j.Slf4j
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

@Slf4j
@Component
class FrankfurterRestClient : FrankfurterClient {

    private val url = "https://api.frankfurter.app/"

    @Cacheable("rates")
    override fun getRates(): String? {
        val requestUrl = url + "latest"

        val getRequest = HttpRequest.newBuilder()
                .uri(URI(requestUrl))
                .GET()
                .build()

        val httpClient = HttpClient.newHttpClient()

        try {
            val response = httpClient.send(getRequest, BodyHandlers.ofString())
            return response.body()
        } catch (e: Exception) {
            Logger.logger.error(e.message)
            throw e
        }
    }
}