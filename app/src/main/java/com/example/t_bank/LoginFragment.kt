package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.t_bank.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        binding.btnRegister.setOnClickListener {
            onRegisterClicked()
        }

        binding.tvForgotPassword.setOnClickListener {
            onForgotPasswordClicked()
        }

        binding.btnLoginWith.setOnClickListener {
            onLoginWithClicked()
        }
        binding.btnCreateAccount.setOnClickListener {
            onCreateAccountClicked()
        }
    }

    private fun onRegisterClicked() {
    }

    private fun onForgotPasswordClicked() {
    }

    private fun onLoginWithClicked() {
    }

    private fun onCreateAccountClicked() {
    }
}