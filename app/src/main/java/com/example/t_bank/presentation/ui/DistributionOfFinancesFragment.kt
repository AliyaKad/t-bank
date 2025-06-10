package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.util.Log
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDistributionOfFinancesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setupBackButton()
        setupSettingsButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    showShimmers()

                    viewModel.categories.collect { categories ->
                        setupPieChart(categories)
                        setupRecyclerView(categories)
                    }
                }

                launch {
                    viewModel.totalBudget.collect { totalBudget ->
                        binding.pieChart.centerText =
                            getString(R.string.total, totalBudget.toString())
                    }
                }
            }
        }
    }

    private fun showShimmers() {
        binding.shimmerPieChart.visibility = View.VISIBLE
        binding.shimmerRecyclerView.visibility = View.VISIBLE
        binding.shimmerPieChart.startShimmer()
        binding.shimmerRecyclerView.startShimmer()

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

    private fun setupPieChart(categories: List<Category>) {
        val entries = categories.map { PieEntry(it.percentage, it.name) }

        with(binding.pieChart) {
            description.isEnabled = false
            legend.isEnabled = false
            holeRadius = 70f
            setUsePercentValues(false)
            setDrawEntryLabels(false)
            setCenterTextSize(18f)
            setDrawMarkers(false)

            val dataSet = PieDataSet(entries, "").apply {
                colors = categories.map { requireContext().getColor(it.colorResId) }
                valueTextSize = 0f
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
        hideShimmers()
    }

    private fun setupBackButton() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupSettingsButton() {
        val emptyCategory = Category(
            name = "",
            colorResId = 0,
            iconResId = 0,
            percentage = 0f
        )
        binding.imgSettings.setOnClickListener {
            val action =
                DistributionOfFinancesFragmentDirections.actionDistributionOfFinancesFragmentToFirstSettingsFragment(
                    emptyCategory
                )
            findNavController().navigate(action)
        }
    }
}
