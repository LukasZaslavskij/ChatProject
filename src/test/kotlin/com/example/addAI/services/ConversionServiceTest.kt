package com.example.addAI.services

import com.example.addAI.Utils.RatesJson
import com.example.addAI.clients.frankfurter.FrankfurterRestClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.web.server.ResponseStatusException

class ConversionServiceTest {

    private val frankfurterMockClient = Mockito.mock(FrankfurterRestClient::class.java)
    private val conversionService = ConversionService(frankfurterMockClient)
    private val rates = RatesJson()

    @Test
    fun convertRateTest() {
        Mockito.`when`(frankfurterMockClient.getRates()).thenReturn(rates.getRates())
        val result = conversionService.convertRate("Convert 10.0 USD to CZK")
        assertEquals("250.0", result)
    }

    @Test
    fun convertRateTestWrongParameters() {
        Mockito.`when`(frankfurterMockClient.getRates()).thenReturn(rates.getRates())
        assertThrows<ResponseStatusException> {
            conversionService.convertRate("Convert 10 USD to CZF")
        }
    }

    @Test
    fun convertRateTestWrongParameterType() {
        Mockito.`when`(frankfurterMockClient.getRates()).thenReturn(rates.getRates())
        assertThrows<ResponseStatusException> {
            conversionService.convertRate("Convert 10.0.0 USD to CZF")
        }
    }
}