package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.presentation.adapter.GoalAdapter
import com.example.t_bank.databinding.FragmentFinancialGoalsBinding
import com.example.t_bank.presentation.model.Goal
import com.example.t_bank.presentation.viewModel.FinancialGoalsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FinancialGoalsFragment : Fragment() {

    private val viewModel: FinancialGoalsViewModel by viewModels()

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
        loadGoals()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = GoalAdapter(emptyList())
        }
    }

    private fun loadGoals() {
        lifecycleScope.launch {
            viewModel.loadGoals(userId = 1)
            viewModel.goals.collect { goals ->
                (binding.recyclerView.adapter as? GoalAdapter)?.updateData(goals)
            }
        }
    }
}
