package com.example.t_bank.data.remote.api


import com.example.t_bank.data.model.BudgetRequest
import com.example.t_bank.data.model.BudgetStatus
import com.example.t_bank.data.model.MessageResponse
import retrofit2.Response
import retrofit2.http.*

interface BudgetApiService {

    @POST("/api/v1/budget")
    suspend fun setBudget(@Body budgetRequest: BudgetRequest): Response<BudgetStatus>

    @GET("/api/v1/budget/status/{userId}")
    suspend fun getBudgetStatus(@Path("userId") userId: Long): Response<BudgetStatus>

    @PATCH("/api/v1/budget/{userId}")
    suspend fun updateIncome(
        @Path("userId") userId: Int,
        @Body incomeUpdate: Map<String, Double>
    ): Response<MessageResponse>
}
