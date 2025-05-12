package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.databinding.FragmentFirstSettingsBinding
import com.example.t_bank.presentation.adapter.FirstSettingsAdapter
import com.example.t_bank.presentation.viewModel.FirstSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle

@AndroidEntryPoint
class FirstSettingsFragment : Fragment() {

    private var _binding: FragmentFirstSettingsBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }
    private val viewModel: FirstSettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FirstSettingsFragment", "onCreateView called")
        _binding = FragmentFirstSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FirstSettingsFragment", "onViewCreated called")

        setupRecyclerView()
        observeCategories()
        setupNextButton()
    }

    private fun setupRecyclerView() {
        Log.d("FirstSettingsFragment", "Setting up RecyclerView...")

        val adapter = FirstSettingsAdapter { category ->
            Log.d("FirstSettingsFragment", "Category clicked: $category")
            val action = FirstSettingsFragmentDirections.actionFirstSettingsFragmentToChangeCategorySettingsFragment(category)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
            Log.d("FirstSettingsFragment", "RecyclerView adapter set")
        }
    }

    private fun observeCategories() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->
                    Log.d("FirstSettingsFragment", "Categories collected in fragment: $categories")

                    (binding.recyclerView.adapter as? FirstSettingsAdapter)?.submitList(categories)
                    if (categories.isEmpty()) {
                        Log.w("FirstSettingsFragment", "No categories available. Check ViewModel or Repository.")
                    }
                }
            }
        }
    }

    private fun setupNextButton() {
        Log.d("FirstSettingsFragment", "Setting up Next Button...")
        binding.btnNext.setOnClickListener {
            Log.d("FirstSettingsFragment", "Next Button clicked")
            val totalBudgetInput = binding.tlAmount.editText?.text.toString()
            val totalBudget = totalBudgetInput.toFloatOrNull() ?: run {
                binding.tlAmount.error = "Введите бюджет"
                Log.e("FirstSettingsFragment", "Invalid budget input")
                return@setOnClickListener
            }
            viewModel.saveBudgetAndCategories(totalBudget)
            val action = FirstSettingsFragmentDirections.actionFirstSettingsFragmentToPercentageDistributionFragment(totalBudget)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("FirstSettingsFragment", "onDestroyView called")
    }
}