package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("category") val category: String,
    @SerializedName("message") val message: String,
    @SerializedName("level") val level: String
)