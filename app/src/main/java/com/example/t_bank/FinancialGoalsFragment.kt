package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FinancialGoalsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GoalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_financial_goals, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val goals = listOf(
            Goal("Цель 1", "3 556 ₽", "26.03.2026", true),
            Goal("Цель 2", "10 000 ₽", "01.01.2027", false),
            Goal("Цель 3", "5 000 ₽", "15.08.2025", true)
        )

        adapter = GoalAdapter(goals)
        recyclerView.adapter = adapter

        return view
    }
}