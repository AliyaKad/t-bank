package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentNewGoalMakingBinding
import com.example.t_bank.presentation.viewModel.NewGoalMakingViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import com.example.t_bank.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    private var isEditMode = false
    private var goalId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val goalId = it.getInt("goalId", -1)
            val goalName = it.getString("goalName")
            val goalAmount = it.getFloat("goalAmount", 0f)
            val goalDate = it.getString("goalDate")

            Log.d("NewGoalMakingFragment", "Received arguments: goalId=$goalId, goalName=$goalName, goalAmount=$goalAmount, goalDate=$goalDate")

            if (goalId != -1 && goalName != null && goalDate != null) {
                isEditMode = true
                setupEditMode(goalName, goalAmount.toDouble(), goalDate)
            }
        }

        setupClickListeners()
        observeViewModel()
    }

    private fun setupEditMode(goalName: String, goalAmount: Double, goalDate: String) {

        binding.tvTitle.text = getString(R.string.edit_goal_title)
        Log.d("SetupEditMode", "Setting up edit mode with data: name=$goalName, amount=$goalAmount, date=$goalDate")
        binding.tlGoalName.editText?.setText(goalName)
        binding.tlAmount.editText?.setText(goalAmount.toString())
        binding.tvDate.text = goalDate
        binding.btnFinish.text = getString(R.string.update_goal)
    }

    private fun onFinishClicked() {
        val goalName = binding.tlGoalName.editText?.text.toString()
        val amount = binding.tlAmount.editText?.text.toString().toDoubleOrNull()
        val date = binding.tvDate.text.toString()

        if (goalName.isNotEmpty() && amount != null && date.isNotEmpty()) {
            if (isEditMode && goalId != null) {
                viewModel.updateGoal(goalId!!, goalName, amount, date, userId = 1)
            } else {
                viewModel.createGoal(goalName, amount, date, userId = 1)
            }
        } else {
            showError(getString(R.string.please_fill_all_fields_correctly))
        }
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

        binding.imgBack.setOnClickListener {
            Log.d("NewGoalMakingFragment", "Back button clicked")
            findNavController().navigateUp()
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


    private fun observeViewModel() {
        viewModel.createGoalResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    showSuccess(getString(R.string.goal_created_successfully))
                    findNavController().navigateUp()
                }
                is Result.Failure -> {
                    showError(getString(R.string.failed_to_create_goal, result.exception.message))
                }
            }
        }

        viewModel.updateGoalResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    showSuccess(getString(R.string.goal_updated_successfully))
                    findNavController().navigateUp()
                }
                is Result.Failure -> {
                    showError(getString(R.string.failed_to_update_goal, result.exception.message))
                }
            }
        }
    }

    private fun showSuccess(message: String) {
    }

    private fun showError(message: String) {
    }
}
