package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.t_bank.databinding.FragmentAddingMoneyToBankBinding
import com.example.t_bank.presentation.viewModel.AddingMoneyToBankViewModel

class AddingMoneyToBankFragment : Fragment() {

    private var _binding: FragmentAddingMoneyToBankBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }
    private val viewModel: AddingMoneyToBankViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddingMoneyToBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.btnAddAmount.setOnClickListener {
            val amount = binding.tlAmount.editText?.text.toString()
            viewModel.updateAmount(amount)
            viewModel.onAddAmountClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                binding.tlAmount.error = errorMessage
            } else {
                binding.tlAmount.error = null
            }
        }
    }
}