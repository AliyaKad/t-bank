package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class Rule(
    @SerializedName("id") val id: Int,
    @SerializedName("field") val field: String,
    @SerializedName("value") val value: String,
    @SerializedName("category") val category: String
)
