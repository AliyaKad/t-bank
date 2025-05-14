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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.t_bank.R
import com.example.t_bank.presentation.model.Category

@AndroidEntryPoint
class FirstSettingsFragment : Fragment() {

    private var _binding: FragmentFirstSettingsBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }
    private val viewModel: FirstSettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val updatedCategory = arguments?.getParcelable<Category>("updatedCategory")
        if (updatedCategory != null) {
            viewModel.updateCategory(updatedCategory)
        }

        setupRecyclerView()
        observeCategories()
        setupNextButton()
    }

    private fun setupRecyclerView() {

        val adapter = FirstSettingsAdapter { category ->
            val action = FirstSettingsFragmentDirections.actionFirstSettingsFragmentToChangeCategorySettingsFragment(category)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun observeCategories() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->

                    (binding.recyclerView.adapter as? FirstSettingsAdapter)?.submitList(categories)
                }
            }
        }
    }

    private fun setupNextButton() {
        binding.btnNext.setOnClickListener {
            val totalBudgetInput = binding.tlAmount.editText?.text.toString()
            val totalBudget = totalBudgetInput.toFloatOrNull() ?: run {
                binding.tlAmount.error = getString(R.string.enter_budget)
                return@setOnClickListener
            }

            val categories = viewModel.categories.value.toTypedArray()
            val action = FirstSettingsFragmentDirections.actionFirstSettingsFragmentToPercentageDistributionFragment(
                totalBudget = totalBudget,
                categories = categories
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}