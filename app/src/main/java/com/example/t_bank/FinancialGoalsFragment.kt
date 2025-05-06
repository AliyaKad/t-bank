package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.adapter.GoalAdapter
import com.example.t_bank.databinding.FragmentFinancialGoalsBinding

class FinancialGoalsFragment : Fragment() {

    private var _binding: FragmentFinancialGoalsBinding? = null

    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinancialGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val goals = listOf(
            Goal("Цель 1", "3 556 ₽", "26.03.2026", true),
            Goal("Цель 2", "10 000 ₽", "01.01.2027", false),
            Goal("Цель 3", "5 000 ₽", "15.08.2025", true)
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = GoalAdapter(goals)
        }
    }
}
