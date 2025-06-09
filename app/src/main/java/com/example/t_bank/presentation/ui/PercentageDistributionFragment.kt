package com.example.t_bank.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.R
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.presentation.adapter.CategoryAdapter
import com.example.t_bank.databinding.FragmentPercentageDistributionBinding
import com.example.t_bank.presentation.viewModel.PercentageDistributionViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PercentageDistributionFragment : Fragment() {

    private var _binding: FragmentPercentageDistributionBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel: PercentageDistributionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPercentageDistributionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalBudget = arguments?.getFloat("totalBudget") ?: 0f
        val categories = arguments?.getParcelableArray("categories")?.map {
            it as Category
        } ?: emptyList()

        binding.pieChart.centerText = context?.getString(R.string.amount_format, totalBudget.toInt())
        viewModel.loadCategories(categories)

        setupRecyclerView()
        observeCategories()
        setupSaveButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val adapter = CategoryAdapter(emptyList()) { updatedCategory: Category ->
            viewModel.updateCategory(updatedCategory)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun observeCategories() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->
                    updatePieChart(categories)
                    (binding.recyclerView.adapter as CategoryAdapter).submitList(categories)
                }
            }
        }
    }

    private fun updatePieChart(categories: List<Category>) {
        val entries = categories.map { PieEntry(it.percentage, it.name) }

        with(binding.pieChart) {
            setUsePercentValues(true)
            description.isEnabled = false
            legend.isEnabled = false

            holeRadius = 70f

            val dataSet = PieDataSet(entries, "").apply {
                colors = categories.map { requireContext().getColor(it.colorResId) }
            }
            data = PieData(dataSet)
            invalidate()
        }
    }

    private fun getCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }


    private fun setupSaveButton() {
        binding.btnFinish.setOnClickListener {
            val totalBudget = arguments?.getFloat("totalBudget") ?: 0f
            val month = getCurrentMonth()
            viewModel.saveDataToDatabase(month, totalBudget)

            val sharedPreferences = requireContext().getSharedPreferences("app_data", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("settings_completed", true).apply()

            findNavController().navigate(R.id.action_percentageDistributionFragment_to_expensesFragment)
        }
    }
}
