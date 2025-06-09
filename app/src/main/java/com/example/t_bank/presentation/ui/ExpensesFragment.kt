package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentExpensesBinding
import com.example.t_bank.presentation.viewModel.ExpensesViewModel
import com.example.t_bank.presentation.adapter.ExpenseAdapter
import com.example.t_bank.presentation.model.Expense
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpensesFragment : Fragment() {

    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel by viewModels<ExpensesViewModel>()

    private lateinit var adapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
        viewModel.loadBudgetStatus()
    }

    private fun setupRecyclerView() {
        adapter = ExpenseAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ExpensesFragment.adapter
        }
    }

    private fun observeData() {
        viewModel.expenses.observe(viewLifecycleOwner) { expenseList ->
            adapter.submitList(expenseList)
            updatePieChart(expenseList)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg ?: "Ошибка", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePieChart(expenses: List<Expense>) {
        val totalSpent = expenses.sumOf { it.spentAmount.toDouble() }
        val totalBudget = expenses.sumOf { it.totalAmount.toDouble() }
        val remaining = totalBudget - totalSpent

        val entries = mutableListOf<PieEntry>()

        entries.addAll(
            expenses.map { expense ->
                PieEntry(expense.spentAmount, expense.category)
            }
        )
        if (remaining > 0) {
            entries.add(PieEntry(remaining.toFloat()))
        }

        with(binding.pieChart) {
            description.isEnabled = false
            legend.isEnabled = false
            holeRadius = 70f
            centerText = context.getString(R.string.spended, totalSpent)
            setUsePercentValues(false)
            setDrawEntryLabels(false)
            setDrawMarkers(false)
            setCenterTextSize(18f)

            val dataSet = PieDataSet(entries, "").apply {
                colors = expenses.map { requireContext().getColor(it.colorResId) } + listOf(
                    ContextCompat.getColor(requireContext(), R.color.gray)
                )
                valueTextSize = 0f
            }

            data = PieData(dataSet)
            invalidate()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ExpenseAdapter(getExpenseData()) { categoryName ->
                findNavController().navigate(
                    R.id.action_expensesFragment_to_transactionsByCategoryFragment,
                    Bundle().apply {
                        putString("categoryName", categoryName)
                    }
                )
            }
        }
    }
}
