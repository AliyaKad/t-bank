package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentSpendingsHistoryBinding
import com.example.t_bank.domain.usecase.model.BudgetForAllMonths
import com.example.t_bank.presentation.adapter.SpendingAdapter
import com.example.t_bank.presentation.model.CategoryForMonths
import com.example.t_bank.presentation.viewModel.SpendingsHistoryViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpendingsHistoryFragment : Fragment() {

    private var _binding: FragmentSpendingsHistoryBinding? = null
    private val binding get() = _binding!!

    private var currentMonthIndex = 0
    private lateinit var viewModel: SpendingsHistoryViewModel
    private lateinit var adapter: SpendingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpendingsHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SpendingsHistoryViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.budgetsForAllMonths.observe(viewLifecycleOwner) { budgets ->
            updateUI(budgets)
        }

        binding.btnPreviousMonth.setOnClickListener {
            if (currentMonthIndex > 0) {
                currentMonthIndex--
                updateUI(viewModel.budgetsForAllMonths.value ?: emptyList())
            }
        }

        binding.btnNextMonth.setOnClickListener {
            if (currentMonthIndex < viewModel.budgetsForAllMonths.value?.size?.minus(1) ?: 0) {
                currentMonthIndex++
                updateUI(viewModel.budgetsForAllMonths.value ?: emptyList())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(budgets: List<BudgetForAllMonths>) {
        val currentMonthData = budgets.getOrNull(currentMonthIndex)

        if (currentMonthData != null) {
            val categoriesWithPercentages = viewModel.getCategoryData(currentMonthData.categories)
            setupPieChart(budgets, categoriesWithPercentages)

            val expenses = viewModel.getSpendingData(currentMonthData.categories)
            Log.d("SpendingsHistoryFrag", "Setting up RecyclerView with ${expenses.size} expenses.")

            if (!::adapter.isInitialized) {
                adapter = SpendingAdapter(expenses)
                binding.recyclerView.adapter = adapter
            } else {
                adapter.submitList(expenses)
            }
        } else {
            Log.e("SpendingsHistoryFrag", "No data for the selected month.")
        }
    }

    private fun setupPieChart(budgets: List<BudgetForAllMonths>, categories: List<CategoryForMonths>) {
        val currentMonthData = budgets.getOrNull(currentMonthIndex)
        val totalSpent = categories.sumOf { it.amountSpent.toDouble() }
        val entries = categories.map { PieEntry(it.percentage.toFloat(), it.name) }

        with(binding.pieChart) {
            setUsePercentValues(true)
            description.isEnabled = false
            legend.isEnabled = false

            val formattedMonth = formatMonth(currentMonthData?.month ?: "2025-05")
            centerText = getString(R.string.pie_chart_center_text, formattedMonth, totalSpent)
            holeRadius = 70f

            val dataSet = PieDataSet(entries, "").apply {
                colors = categories.map { requireContext().getColor(it.colorResId) }
            }
            data = PieData(dataSet)
            invalidate()
        }
    }

    private fun formatMonth(month: String): String {
        val parts = month.split("-")
        val year = parts[0]
        val monthNumber = parts[1].toIntOrNull() ?: 1
        val monthName = when (monthNumber) {
            1 -> "Январь"
            2 -> "Февраль"
            3 -> "Март"
            4 -> "Апрель"
            5 -> "Май"
            6 -> "Июнь"
            7 -> "Июль"
            8 -> "Август"
            9 -> "Сентябрь"
            10 -> "Октябрь"
            11 -> "Ноябрь"
            12 -> "Декабрь"
            else -> "Неизвестный месяц"
        }
        return "$monthName $year"
    }
}
