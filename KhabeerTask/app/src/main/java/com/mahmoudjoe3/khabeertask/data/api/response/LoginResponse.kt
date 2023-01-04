package com.mahmoudjoe3.khabeertask.data.api.response

data class LoginResponse(
    val Token: String? = null,
    val Success: Boolean,
    val EnglishMessage: String,
    val ArabicMessage: String
)