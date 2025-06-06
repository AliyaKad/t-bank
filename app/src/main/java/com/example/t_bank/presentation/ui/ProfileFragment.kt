package com.example.t_bank.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isDarkTheme = loadTheme()
        binding.switchDarkTheme.isChecked = isDarkTheme

        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.cardMyFinancials.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_distributionOfFinancesFragment)
        }

        binding.cardSpendingHistory.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_spendingsHistoryFragment)
        }

        binding.cardChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_profileRedactionFragment)
        }

        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            saveTheme(isChecked)
            if (isChecked) {
                setAppTheme(R.style.Theme_Tbank_Dark)
            } else {
                setAppTheme(R.style.Theme_Tbank)
            }
        }
    }

    private fun saveTheme(isDarkTheme: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("is_dark_theme", isDarkTheme).apply()
    }

    private fun loadTheme(): Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_dark_theme", false)
    }

    private fun setAppTheme(themeResId: Int) {
        saveTheme(themeResId == R.style.Theme_Tbank_Dark)
        activity?.recreate()
    }
}

