package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.MessageResponse
import com.example.t_bank.data.model.Rule
import com.example.t_bank.data.model.RuleRequest
import com.example.t_bank.data.remote.api.SmartBudgetApiService

class RuleDataSource(private val apiService: SmartBudgetApiService) {

    suspend fun createRule(userId: Int, rule: RuleRequest): MessageResponse? {
        val response = apiService.createRule(userId, rule)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getRules(userId: Int): List<Rule>? {
        val response = apiService.getRules(userId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateRule(userId: Int, ruleId: Int, rule: RuleRequest): MessageResponse? {
        val response = apiService.updateRule(userId, ruleId, rule)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteRule(userId: Int, ruleId: Int): MessageResponse? {
        val response = apiService.deleteRule(userId, ruleId)
        return if (response.isSuccessful) response.body() else null
    }
}
