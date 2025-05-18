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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FinancialGoalsFragment : Fragment() {

    private var _binding: FragmentFinancialGoalsBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel: FinancialGoalsViewModel by viewModels()

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
        lifecycleScope.launch {
            viewModel.goals.collect { goals ->
                val goalList = goals.map { entity ->
                    Goal(
                        name = entity.goalName,
                        amount = "${entity.amount} ₽",
                        endDate = entity.endDate,
                        isAchieved = entity.endDate < getCurrentDate()
                    )
                }
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = GoalAdapter(goalList)
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
