package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.t_bank.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null

    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
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
        binding.btnGetCode.setOnClickListener {
            onGetCodeClicked()
        }

        binding.btnLoginWithGoogle.setOnClickListener {
            onLoginWithClicked()
        }

        binding.btnLoginExistingAccount.setOnClickListener {
            onLoginExistingAccountClicked()
        }
    }

    private fun onGetCodeClicked() {
    }

    private fun onLoginWithClicked() {
    }

    private fun onLoginExistingAccountClicked() {
    }
}
