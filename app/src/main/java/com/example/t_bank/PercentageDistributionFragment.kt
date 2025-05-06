package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.adapter.CategoryAdapter
import com.example.t_bank.databinding.FragmentPercentageDistributionBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PercentageDistributionFragment : Fragment() {

    private var _binding: FragmentPercentageDistributionBinding? = null

    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPercentageDistributionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val adapter = CategoryAdapter(getCategoryData()).apply {
            onCategoryUpdated = {
                updatePieChart()
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun updatePieChart() {
        val adapter = binding.recyclerView.adapter as CategoryAdapter
        val categories = adapter.getCategories()
        val entries = categories.map { PieEntry(it.percentage, it.name) }

        with(binding.pieChart) {
            data = PieData(PieDataSet(entries, ""))
            invalidate()
        }
    }

    private fun getCategoryData(): List<Category> {
        return listOf(
            Category("Продукты",1, 25f),
            Category("Транспорт",1, 15f),
            Category("Коммунальные услуги",1, 10f),
            Category("Развлечения",1, 20f),
            Category("Другое",1, 30f)
        )
    }
}
