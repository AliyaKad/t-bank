package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String,

    @SerializedName("password")
    val password: String
)
