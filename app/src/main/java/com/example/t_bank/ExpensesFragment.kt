package com.example.t_bank

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry


class ExpensesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)

        val pieChart = view.findViewById<PieChart>(R.id.pieChart)
        setupPieChart(pieChart)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ExpenseAdapter(getExpenseData())

        return view
    }

    private fun setupPieChart(pieChart: PieChart) {
        val entries = listOf(
            PieEntry(20f, "Категория 1"),
            PieEntry(30f, "Категория 2"),
            PieEntry(50f, "Категория 3")
        )

        pieChart.setUsePercentValues(false)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false

        pieChart.centerText = "35 000 ₽\nпотрачено"

        pieChart.holeRadius = 70f

        val dataSet = com.github.mikephil.charting.data.PieDataSet(entries, "")
        dataSet.colors = listOf(
            Color.parseColor("#FFC107"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#E91E63")
        )
        pieChart.data = com.github.mikephil.charting.data.PieData(dataSet)
        pieChart.invalidate()
    }

    private fun getExpenseData(): List<Expense> {
        return listOf(
            Expense("Продукты", 20000f, 10000f),
            Expense("Коммунальные услуги", 20000f, 10000f)
        )
    }
}
