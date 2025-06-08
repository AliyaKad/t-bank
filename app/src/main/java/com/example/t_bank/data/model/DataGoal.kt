package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class DataGoal(
    @SerializedName("name") val name: String,
    @SerializedName("targetAmount") val targetAmount: Double,
    @SerializedName("savedAmount") val savedAmount: Double,
    @SerializedName("recommendedMonthlySaving") val recommendedMonthlySaving: Double,
    @SerializedName("deadline") val deadline: String,
    @SerializedName("status") val status: String
)
