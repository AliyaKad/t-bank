package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.t_bank.databinding.FragmentPasswordCreatingBinding

class PasswordCreatingFragment : Fragment() {

    private var _binding: FragmentPasswordCreatingBinding? = null

    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordCreatingBinding.inflate(inflater, container, false)
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
        binding.btnConfirm.setOnClickListener {
            onConfirmClicked()
        }
    }

    private fun onConfirmClicked() {

        val password = binding.tPassword.editText?.text.toString()
        val confirmPassword = binding.tlConfirmPassword.editText?.text.toString()

        if (password == confirmPassword && password.isNotEmpty()) {
        } else {
        }
    }
}