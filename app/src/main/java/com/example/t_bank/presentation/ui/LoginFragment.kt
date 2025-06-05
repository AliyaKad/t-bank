package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentLoginBinding
import com.example.t_bank.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = viewModels<LoginViewModel>().value

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.loginState.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is LoginViewModel.LoginResult.Loading -> {
                    binding.btnLoginWith.text = ""
                    binding.btnLoginWith.isEnabled = false
                }
                is LoginViewModel.LoginResult.Success -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
                is LoginViewModel.LoginResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    binding.btnLoginWith.isEnabled = true
                }
            }
        })
    }

    private fun setupClickListeners() {
        binding.tvForgotPassword.setOnClickListener {
            onForgotPasswordClicked()
        }

        binding.btnLoginWith.setOnClickListener {
            onLoginWithClicked()
        }
    }

    private fun onForgotPasswordClicked() {
        // TODO: Логика восстановления пароля
    }

    private fun onLoginWithClicked() {
        val phone = binding.tlPhoneNumber.editText?.text.toString()
        val password = binding.tlPassword.editText?.text.toString()

        if (phone.isNotBlank() && password.isNotBlank()) {
            viewModel.login(phone, password)
        } else {
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
