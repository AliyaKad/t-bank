package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class SpendingsHistoryFragment : Fragment() {

    private var _binding: FragmentSpendingsHistoryBinding? = null
    private val binding get() = _binding!!

    private var currentMonthIndex = 0
    private lateinit var adapter: SpendingAdapter
    private val viewModel: SpendingsHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpendingsHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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

        setupBackButton()
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
            setDrawEntryLabels(false)
            setCenterTextSize(18f)
            setDrawMarkers(false)

            val formattedMonth = formatMonth(currentMonthData?.month ?: "2025-05")
            centerText = getString(R.string.pie_chart_center_text, formattedMonth, totalSpent)
            holeRadius = 70f

            val dataSet = PieDataSet(entries, "").apply {
                colors = categories.map { requireContext().getColor(it.colorResId) }
                valueTextSize = 0f
            }
            data = PieData(dataSet)
            invalidate()
        }
    }

    private fun setupBackButton() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun formatMonth(monthYear: String): String {
        val parts = monthYear.split("-")
        val year = parts[0].toInt()
        val monthNumber = parts[1].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(year, monthNumber - 1, 1)

        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        return "$monthName $year"
    }
}
