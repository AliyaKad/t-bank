package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.t_bank.databinding.FragmentNewGoalMakingBinding
import com.example.t_bank.presentation.viewModel.NewGoalMakingViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast

class NewGoalMakingFragment : Fragment() {

    private var _binding: FragmentNewGoalMakingBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel: NewGoalMakingViewModel by viewModels()

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
        val amount = binding.tlAmount.editText?.text.toString().toFloatOrNull()
        val date = binding.tvDate.text.toString()

        if (goalName.isNotEmpty() && amount != null && amount > 0 && date.isNotEmpty()) {
            viewModel.saveFinancialGoal(
                goalName = goalName,
                amount = amount,
                endDate = date,
                onSuccess = {
                    showToast("Цель успешно сохранена!")
                },
                onError = {
                    showToast("Ошибка при сохранении цели")
                }
            )
        } else {
            showToast("Пожалуйста, заполните все поля корректно")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
