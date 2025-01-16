package com.example.authloginmodule

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime

data class Login(
    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @field:SerializedName("facebook_provider")
    val facebookProvider: String? = null,

    @field:SerializedName("gmail_provider")
    val gmailProvider: String? = null,
)

data class Cadastro(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("facebook_id")
    val facebook_id: String?,

    @SerializedName("google_sub")
    val google_sub: String?,

    @SerializedName("biometric_data")
    val biometric_data: String,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("data_nascimento")
    val data_nascimento: String,

    @SerializedName("data_criacao")
    val data_criacao: String
)
