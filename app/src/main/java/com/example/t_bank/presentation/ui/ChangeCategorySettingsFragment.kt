package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.t_bank.presentation.adapter.ColorAdapter
import com.example.t_bank.databinding.FragmentChangeCategorySettingsBinding
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.presentation.viewModel.ChangeCategorySettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChangeCategorySettingsFragment : Fragment() {

    private var _binding: FragmentChangeCategorySettingsBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel: ChangeCategorySettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeCategorySettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем категорию из аргументов
        val category = arguments?.getParcelable<Category>("category")
        if (category != null) {
            viewModel.loadCategory(category.name)
        }

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.colors.collectLatest { colors ->
                    val adapter = ColorAdapter(colors) { selectedColor ->
                        val category = viewModel.selectedCategory.value
                        if (category != null) {
                            val updatedCategory = category.copy(colorResId = selectedColor)
                            viewModel.updateCategoryColor(updatedCategory)
                            findNavController().popBackStack()
                        }
                    }

                    binding.recyclerViewColors.apply {
                        layoutManager = GridLayoutManager(requireContext(), 5)
                        this.adapter = adapter
                    }
                }
            }
        }
    }
}
