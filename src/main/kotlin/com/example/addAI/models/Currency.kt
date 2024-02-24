package com.example.addAI.models

data class Currency(
        val amount: Double,
        val base: String,
        val date: String,
        val rates: Map<String, Double>
)
