package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.adapter.DistributionCategoryAdapter
import com.example.t_bank.databinding.FragmentDistributionOfFinancesBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class DistributionOfFinancesFragment : Fragment() {

    private var _binding: FragmentDistributionOfFinancesBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDistributionOfFinancesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPieChart()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupPieChart() {
        val entries = getCategoryData().map { PieEntry(it.percentage, it.name) }

        with(binding.pieChart) {
            setUsePercentValues(true)
            description.isEnabled = false
            legend.isEnabled = false

            centerText = "80 000 ₽"
            holeRadius = 70f

            val dataSet = PieDataSet(entries, "").apply {
                colors = listOf(
                    requireContext().getColor(R.color.yellow),
                    requireContext().getColor(R.color.blue),
                    requireContext().getColor(R.color.black),
                    requireContext().getColor(R.color.green),
                    requireContext().getColor(R.color.red)
                )
            }
            data = PieData(dataSet)
            invalidate()
        }
    }

    private fun setupRecyclerView() {
        val adapter = DistributionCategoryAdapter(getCategoryData())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun updatePieChart() {
        val categories = getCategoryData()
        val entries = categories.map { PieEntry(it.percentage, it.name) }

        with(binding.pieChart) {
            data = PieData(PieDataSet(entries, ""))
            invalidate()
        }
    }

    private fun getCategoryData(): List<Category> {
        return listOf(
            Category("Продукты", R.drawable.ic_food, 25f),
            Category("Транспорт", R.drawable.ic_transport, 15f),
            Category("Коммунальные услуги", R.drawable.ic_utilities, 10f),
            Category("Развлечения", R.drawable.ic_entertainment, 10f),
            Category("Другое", R.drawable.ic_other, 40f)
        )
    }
}
