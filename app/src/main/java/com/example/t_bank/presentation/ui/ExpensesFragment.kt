package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        showShimmers()
        setupRecyclerView()
        observeData()
        viewModel.loadBudgetStatus()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ExpenseAdapter { categoryName ->
            findNavController().navigate(
                R.id.action_expensesFragment_to_transactionsByCategoryFragment,
                Bundle().apply {
                    putString("categoryName", categoryName)
                }
            )
        }
        binding.recyclerView.adapter = adapter
    }

    private fun observeData() {
        viewModel.expenses.observe(viewLifecycleOwner) { expenseList ->
            adapter.submitList(expenseList)
            updatePieChart(expenseList)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            Log.d("Exp", "${errorMsg}")
        }
        hideShimmers()
    }

    private fun updatePieChart(expenses: List<Expense>) {
        if (expenses.isEmpty()) {
            return
        }

        val totalSpent = expenses.sumOf { it.spentAmount.toDouble() }
        val totalBudget = expenses.sumOf { it.totalAmount.toDouble() }
        val remaining = totalBudget - totalSpent

        val entries = expenses.map { expense ->
            PieEntry(expense.spentAmount, expense.category)
        }.toMutableList()

        if (remaining > 0) {
            entries.add(PieEntry(remaining.toFloat()))
        }

        with(binding.pieChart) {
            clear()

            description.isEnabled = false
            legend.isEnabled = false
            holeRadius = 70f
            centerText = getString(R.string.spended, totalSpent)
            setUsePercentValues(false)
            setDrawEntryLabels(false)
            setCenterTextSize(18f)

            val dataSet = PieDataSet(entries, "").apply {
                colors = expenses.map { requireContext().getColor(it.colorResId) } + R.color.gray.let {
                    ContextCompat.getColor(requireContext(), it)
                }
                valueTextSize = 0f
            }

            data = PieData(dataSet)
            invalidate()
        }
    }

    private fun showShimmers() {
        binding.shimmerPieChart.startShimmer()
        binding.shimmerRecyclerView.startShimmer()
        binding.shimmerPieChart.visibility = View.VISIBLE
        binding.shimmerRecyclerView.visibility = View.VISIBLE

        binding.pieChart.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
    }

    private fun hideShimmers() {
        binding.shimmerPieChart.stopShimmer()
        binding.shimmerRecyclerView.stopShimmer()
        binding.shimmerPieChart.visibility = View.GONE
        binding.shimmerRecyclerView.visibility = View.GONE

        binding.pieChart.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
