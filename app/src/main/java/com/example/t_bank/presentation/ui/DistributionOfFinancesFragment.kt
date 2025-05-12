package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.R
import com.example.t_bank.presentation.adapter.DistributionCategoryAdapter
import com.example.t_bank.databinding.FragmentDistributionOfFinancesBinding
import com.example.t_bank.presentation.viewModel.DistributionOfFinancesViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DistributionOfFinancesFragment : Fragment() {

    private var _binding: FragmentDistributionOfFinancesBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel: DistributionOfFinancesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDistributionOfFinancesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->
                    setupPieChart(categories)
                    setupRecyclerView(categories)
                }

                viewModel.totalBudget.collect { totalBudget ->
                    binding.pieChart.centerText = "${totalBudget.toInt()} ₽"
                }
            }
        }
    }

    private fun setupPieChart(categories: List<Category>) {
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

    private fun setupRecyclerView(categories: List<Category>) {
        val adapter = DistributionCategoryAdapter(categories)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }
}
