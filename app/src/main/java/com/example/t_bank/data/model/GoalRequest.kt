package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class GoalRequest(
    @SerializedName("userId") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("targetAmount") val targetAmount: Double,
    @SerializedName("deadline") val deadline: String
)
