package com.example.t_bank.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.R
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

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_financialGoalsFragment_to_newGoalMakingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = GoalAdapter(
                emptyList(),
                onItemClick = { goal ->
                    navigateToAddMoneyToBankFragment(goal)
                },
                onLongClick = { goal ->
                    showEditOrDeleteDialog(goal)
                }
            )
        }
    }

    private fun showDeleteConfirmationDialog(goal: Goal) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.Do_you_want_delete_goal))
            .setMessage(getString(R.string.Are_you_shure_delete, goal.name))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteGoal(userId = 1, goalId = goal.id)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showEditOrDeleteDialog(goal: Goal) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.choose_a_move))
            .setItems(arrayOf(getString(R.string.change), getString(R.string.delete) )) { _, which ->
                when (which) {
                    0 -> navigateToEditGoal(goal)
                    1 -> showDeleteConfirmationDialog(goal)
                }
            }
            .show()
    }

    private fun navigateToEditGoal(goal: Goal) {
        Log.d("NavigateToEditGoal", "Navigating to edit goal with data: $goal")
        val action = FinancialGoalsFragmentDirections
            .actionFinancialGoalsFragmentToNewGoalMakingFragment(
                goalId = goal.id,
                goalName = goal.name,
                goalAmount = goal.amount.toFloat(),
                goalDate = goal.endDate
            )
        Log.d("NavigateToEditGoal", "Navigation action created: $action")
        findNavController().navigate(action)
    }

    private fun navigateToAddMoneyToBankFragment(goal: Goal) {
        Log.d("NavigateToAddMoney", "Navigating to AddingMoneyToBankFragment with data: $goal")
        val action = FinancialGoalsFragmentDirections
            .actionFinancialGoalsFragmentToAddingMoneyToBankFragment(
                goalId = goal.id,
                goalName = goal.name,
                goalAmount = goal.amount.toFloat(),
                goalDate = goal.endDate
            )
        findNavController().navigate(action)
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
