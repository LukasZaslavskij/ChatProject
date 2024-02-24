package com.example.addAI.services

import com.example.addAI.clients.frankfurter.FrankfurterClient
import com.example.addAI.models.Currency
import com.example.addAI.models.CurrencyChangeParameters
import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class ConversionService(
        private val frankfurterRestClient: FrankfurterClient
) {

    fun convertRate(message: String): String {
        val currencyParams = getCurrencyParamsFromMessage(message)
        val rates = frankfurterRestClient.getRates()
        if (currencyParams != null && rates != null)
            try {
                return calculateRequestedCurrency(currencyParams, rates)
            } catch (e: Exception) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong conversion parameter: ${e.message}")
            }
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong paramter type")
    }

    private fun getCurrencyParamsFromMessage(message: String): CurrencyChangeParameters? {
        val regex = "(Convert|How much is) (\\d+\\.?\\d*) (\\w+) (in|to) (\\w+)".toRegex()
        val matchResult = regex.find(message)

        if (matchResult != null) {
            val (_, amount, from, _, to) = matchResult.destructured
            return CurrencyChangeParameters(from, to, amount.toDouble())
        }
        return null
    }

    private fun calculateRequestedCurrency(params: CurrencyChangeParameters, rates: String): String {
        val gson = Gson()
        val currency = gson.fromJson(rates, Currency::class.java)

        val from = if (params.currencyFrom == "EUR") 1.0 else currency.rates[params.currencyFrom]
                ?: throw IllegalArgumentException("Invalid currency: ${params.currencyFrom}")
        val to = if (params.currencyTo == "EUR") 1.0 else currency.rates[params.currencyTo]
                ?: throw IllegalArgumentException("Invalid currency: ${params.currencyTo}")
        val amount = params.value

        return (amount / from * to).toString()
    }
}