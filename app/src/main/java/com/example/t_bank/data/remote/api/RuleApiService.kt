package com.example.t_bank.data.remote.api

import com.example.t_bank.data.model.Rule
import com.example.t_bank.data.model.RuleRequest
import com.example.t_bank.data.model.MessageResponse
import retrofit2.Response
import retrofit2.http.*

interface RuleApiService {

    @POST("/api/v1/rules/{userId}")
    suspend fun createRule(
        @Path("userId") userId: Int,
        @Body rule: RuleRequest
    ): Response<MessageResponse>

    @GET("/api/v1/rules/{userId}")
    suspend fun getRules(@Path("userId") userId: Int): Response<List<Rule>>

    @PUT("/api/v1/rules/{userId}/{ruleId}")
    suspend fun updateRule(
        @Path("userId") userId: Int,
        @Path("ruleId") ruleId: Int,
        @Body rule: RuleRequest
    ): Response<MessageResponse>

    @DELETE("/api/v1/rules/{userId}/{ruleId}")
    suspend fun deleteRule(
        @Path("userId") userId: Int,
        @Path("ruleId") ruleId: Int
    ): Response<MessageResponse>
}
