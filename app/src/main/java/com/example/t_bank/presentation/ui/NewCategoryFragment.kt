package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.t_bank.R
import com.example.t_bank.presentation.adapter.ColorAdapter
import com.example.t_bank.databinding.FragmentNewCategoryBinding
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.presentation.viewModel.FirstSettingsViewModel
import com.example.t_bank.presentation.viewModel.NewCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewCategoryFragment : Fragment() {

    private var _binding: FragmentNewCategoryBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    private val viewModel: NewCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSaveButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val colors = listOf(
            R.color.red,
            R.color.orange,
            R.color.yellow,
            R.color.green,
            R.color.blue,
            R.color.purple,
            R.color.pink,
            R.color.brown,
            R.color.black,
            R.color.gray
        )

        val adapter = ColorAdapter(colors) { selectedColor ->
            viewModel.setSelectedColor(selectedColor)
        }

        binding.recyclerViewColors.apply {
            layoutManager = GridLayoutManager(requireContext(), 5)
            this.adapter = adapter
        }
    }

    private fun setupSaveButton() {
        binding.btnAdd.setOnClickListener {
            val name = binding.tlName?.text.toString().trim()
            viewModel.setCategoryName(name)

            val newCategory = viewModel.createCategory()
            if (newCategory != null) {
                val emptyCategory = Category(
                    name = "",
                    colorResId = 0,
                    iconResId = 0,
                    percentage = 0f
                )
                Log.d("NewCategoryFragment", "Created new category: $newCategory")
                findNavController().previousBackStackEntry?.savedStateHandle?.set("newCategory", newCategory)
                findNavController().navigateUp()
            } else {
                Log.e("NewCategoryFragment", "Failed to create category: name or color is missing")
                binding.tlName.error = getString(R.string.enter_category_name)
            }
        }
    }
}
