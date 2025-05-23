package com.example.t_bank.data.remote.api

import com.example.t_bank.data.model.Transaction
import com.example.t_bank.data.model.MessageResponse
import retrofit2.Response
import retrofit2.http.*

interface TransactionApiService {

    @GET("/api/v1/transactions/{userId}")
    suspend fun getTransactions(@Path("userId") userId: Int): Response<List<Transaction>>

    @PUT("/api/v1/transactions/{userId}/{transactionId}")
    suspend fun updateTransactionCategory(
        @Path("userId") userId: Int,
        @Path("transactionId") transactionId: Int,
        @Body categoryUpdate: Map<String, String>
    ): Response<MessageResponse>

    @POST("/api/v1/transactions")
    suspend fun importTransactions(@Body transactions: List<Transaction>): Response<MessageResponse>
}
