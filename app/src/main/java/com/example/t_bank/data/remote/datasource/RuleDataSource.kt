package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.MessageResponse
import com.example.t_bank.data.model.Rule
import com.example.t_bank.data.model.RuleRequest

interface RuleDataSource {
    suspend fun createRule(userId: Int, rule: RuleRequest): MessageResponse?
    suspend fun getRules(userId: Int): List<Rule>?
    suspend fun updateRule(userId: Int, ruleId: Int, rule: RuleRequest): MessageResponse?
    suspend fun deleteRule(userId: Int, ruleId: Int): MessageResponse?
}
