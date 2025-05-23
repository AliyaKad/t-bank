package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.presentation.model.Expense
import com.example.t_bank.R
import com.example.t_bank.presentation.adapter.SpendingAdapter
import com.example.t_bank.databinding.FragmentSpendingsHistoryBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class SpendingsHistoryFragment : Fragment() {

    private var _binding: FragmentSpendingsHistoryBinding? = null
    private val binding get() = _binding!!

    private var currentMonthIndex = 0
    private val months = listOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь")

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

        setupRecyclerView()
        //setupPieChart()

        binding.btnPreviousMonth.setOnClickListener {
            if (currentMonthIndex > 0) {
                currentMonthIndex--
                updateUI()
            }
        }

        binding.btnNextMonth.setOnClickListener {
            if (currentMonthIndex < months.lastIndex) {
                currentMonthIndex++
                updateUI()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI() {
        binding.tvCurrentMonth.text = months[currentMonthIndex]

        //setupPieChart()
        setupRecyclerView()

        if (::adapter.isInitialized) {
            adapter.updateData(getSpendingData(currentMonthIndex))
        } else {
        }
    }

//    private fun setupPieChart() {
//        val entries = getCategoryData(currentMonthIndex).map { PieEntry(it.percentage, it.name) }
//
//        with(binding.pieChart) {
//            setUsePercentValues(true)
//            description.isEnabled = false
//            legend.isEnabled = false
//
//            centerText = "${months[currentMonthIndex]}\n75 433 ₽"
//            holeRadius = 70f
//
//            val dataSet = PieDataSet(entries, "").apply {
//                colors = listOf(
//                    requireContext().getColor(R.color.yellow),
//                    requireContext().getColor(R.color.blue),
//                    requireContext().getColor(R.color.green),
//                    requireContext().getColor(R.color.red)
//                )
//            }
//            data = PieData(dataSet)
//            invalidate()
//        }
//    }

    private fun setupRecyclerView() {
        adapter = SpendingAdapter(getSpendingData(currentMonthIndex))

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SpendingAdapter(getSpendingData(currentMonthIndex))
            this.adapter = adapter
        }
    }

//    private fun getCategoryData(monthIndex: Int): List<Category> {
//        return when (monthIndex) {
//            0 -> listOf(
//                Category("Продукты", R.drawable.ic_food, 25f),
//                Category("Коммунальные услуги", R.drawable.ic_utilities, 15f),
//                Category("Развлечения", R.drawable.ic_entertainment, 10f),
//                Category("Другое", R.drawable.ic_other, 50f)
//            )
//            1 -> listOf(
//                Category("Продукты", R.drawable.ic_food, 30f),
//                Category("Коммунальные услуги", R.drawable.ic_utilities, 20f),
//                Category("Развлечения", R.drawable.ic_entertainment, 15f),
//                Category("Другое", R.drawable.ic_other, 35f)
//            )
//            else -> listOf(
//                Category("Продукты", R.drawable.ic_food, 20f),
//                Category("Коммунальные услуги", R.drawable.ic_utilities, 25f),
//                Category("Развлечения", R.drawable.ic_entertainment, 10f),
//                Category("Другое", R.drawable.ic_other, 45f)
//            )
//        }
//    }

    private fun getSpendingData(monthIndex: Int): List<Expense> {
        return when (monthIndex) {
            0 -> listOf(
                Expense("Продукты", 20000f, 10000f),
                Expense("Коммунальные услуги", 15000f, 7500f)
            )
            1 -> listOf(
                Expense("Продукты", 25000f, 12500f),
                Expense("Коммунальные услуги", 18000f, 9000f)
            )
            else -> listOf(
                Expense("Продукты", 18000f, 9000f),
                Expense("Коммунальные услуги", 16000f, 8000f)
            )
        }
    }
}
