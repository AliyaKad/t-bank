package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.t_bank.databinding.FragmentAddingMoneyToBankBinding

class AddingMoneyToBankFragment : Fragment() {

    private var _binding: FragmentAddingMoneyToBankBinding? = null

    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddingMoneyToBankBinding.inflate(inflater, container, false)
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
        binding.btnAddAmount.setOnClickListener {
            onAddAmountClicked()
        }
    }

    private fun onAddAmountClicked() {

        val amount = binding.tlAmount.editText?.text.toString()

        if (amount.isNotEmpty()) {
        } else {
        }
    }
}
