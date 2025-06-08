package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("userId") val userId: Int,
    @SerializedName("date") val date: String,
    @SerializedName("description") val description: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("category") val category: String
)
