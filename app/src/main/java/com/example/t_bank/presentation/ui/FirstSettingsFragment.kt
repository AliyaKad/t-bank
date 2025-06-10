package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.databinding.FragmentFirstSettingsBinding
import com.example.t_bank.presentation.adapter.FirstSettingsAdapter
import com.example.t_bank.presentation.viewModel.FirstSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.t_bank.R
import com.example.t_bank.launchAndCollectIn
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

        val newCategory = findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Category>("newCategory")?.value
        newCategory?.let {
            Log.d("FirstSettingsFragment", "Received new category from NewCategoryFragment: $it")
            updateCategoryList(it)
        }

        val updatedCategory = arguments?.getParcelable<Category>("updatedCategory")
        updatedCategory?.let { viewModel.updateCategory(it) }


        setupRecyclerView()
        observeCategories()
        setupNextButton()
    }

    private fun updateCategoryList(newCategory: Category) {
        viewModel.addCategory(newCategory)
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
        launchAndCollectIn(viewModel.categories) { categories ->
            (binding.recyclerView.adapter as? FirstSettingsAdapter)?.submitList(categories)
        }
    }

    private fun setupNextButton() {
        binding.btnNext.setOnClickListener {
            val totalBudgetInput = binding.tlAmount?.text.toString()
            val totalBudget = validateBudgetInput(totalBudgetInput) ?: return@setOnClickListener

            val categories = viewModel.categories.value.toTypedArray()
            val action = FirstSettingsFragmentDirections.actionFirstSettingsFragmentToPercentageDistributionFragment(
                totalBudget = totalBudget,
                categories = categories
            )
            findNavController().navigate(action)
        }
    }


    private fun validateBudgetInput(input: String): Float? {
        return input.toFloatOrNull() ?: run {
            binding.tlAmount.error = getString(R.string.enter_budget)
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}