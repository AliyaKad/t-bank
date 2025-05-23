package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.t_bank.databinding.FragmentNewGoalMakingBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class NewGoalMakingFragment : Fragment() {

    private var _binding: FragmentNewGoalMakingBinding? = null

    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewGoalMakingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.tvDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnFinish.setOnClickListener {
            onFinishClicked()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { dateInMillis ->
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val selectedDate = dateFormat.format(dateInMillis).toString()

            binding.tvDate.text = selectedDate
        }

        datePicker.show(childFragmentManager, "DATE_PICKER_TAG")
    }

    private fun onFinishClicked() {

        val goalName = binding.tlGoalName.editText?.text.toString()
        val amount = binding.tlAmount.editText?.text.toString()
        val date = binding.tvDate.text.toString()

        if (goalName.isNotEmpty() && amount.isNotEmpty() && date.isNotEmpty()) {
        } else {
        }
    }
}
