package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.presentation.model.Expense
import com.example.t_bank.R
import com.example.t_bank.presentation.adapter.ExpenseAdapter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.example.t_bank.databinding.FragmentExpensesBinding

class ExpensesFragment : Fragment() {

    private var _binding: FragmentExpensesBinding? = null

    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
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
        val entries = listOf(
            PieEntry(20f, "Категория 1"),
            PieEntry(30f, "Категория 2"),
            PieEntry(50f, "Категория 3")
        )

        with(binding.pieChart) {
            setUsePercentValues(false)
            description.isEnabled = false
            legend.isEnabled = false

            centerText = "35 000 ₽\nпотрачено"
            holeRadius = 70f

            val dataSet = PieDataSet(entries, "").apply {
                colors = listOf(
                    getClr(R.color.yellow),
                    getClr(R.color.pink),
                    getClr(R.color.blue)
                )
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

    private fun getExpenseData(): List<Expense> {
        return listOf(
            Expense(getString(R.string.products), 20000f, 10000f),
            Expense(getString(R.string.communal_services), 20000f, 10000f)
        )
    }

    private fun getClr(colorResId: Int): Int {
        return resources.getColor(colorResId, null)
    }
}

