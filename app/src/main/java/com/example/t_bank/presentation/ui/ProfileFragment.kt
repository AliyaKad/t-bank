package com.example.t_bank.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentProfileBinding
import java.util.Locale

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

        setAppLocale(getCurrentLanguage())
        setupClickListeners()
        setupLanguageSpinner()
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

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun saveLanguage(lang: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("app_language", lang).apply()
    }

    private fun getCurrentLanguage(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("app_language", "ru")!!
    }

    private fun setupLanguageSpinner() {
        val languages = listOf(getString(R.string.russian), getString(R.string.english))
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spinnerLanguage.adapter = adapter
        val currentLang = getCurrentLanguage()
        val position = when (currentLang) {
            "ru" -> 0
            "en" -> 1
            else -> 0
        }
        binding.spinnerLanguage.setSelection(position)

        binding.spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedLang = if (position == 0) "ru" else "en"
                if (selectedLang != currentLang) {
                    saveLanguage(selectedLang)
                    setAppLocale(selectedLang)
                    requireActivity().recreate()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}

