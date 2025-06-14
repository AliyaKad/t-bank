package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.t_bank.presentation.adapter.ColorAdapter
import com.example.t_bank.databinding.FragmentChangeCategorySettingsBinding
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.presentation.viewModel.ChangeCategorySettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import com.example.t_bank.R
import com.example.t_bank.launchAndCollectIn


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

        val category = arguments?.getParcelable<Category>("category")
            ?: requireNotNull(null) { "Category argument is required but was not provided." }
        viewModel.loadCategory(category.name)

        setupRecyclerView()
        setupSaveButton()
        binding.cardCategory.findViewById<TextView>(R.id.tvPercentage).visibility = View.GONE
        binding.cardCategory.findViewById<SeekBar>(R.id.seekBarPercentage).visibility = View.GONE

        launchAndCollectIn(viewModel.selectedCategory) { selectedCategory ->
            if (selectedCategory != null) {
                updateCategoryUI(selectedCategory)
            }
        }
    }

    private fun updateCategoryUI(category: Category) {
        binding.cardCategory.findViewById<TextView>(R.id.tvCategoryName)?.text = category.name
        binding.cardCategory.findViewById<ImageView>(R.id.ivIcon)?.setImageResource(category.iconResId)
        binding.cardCategory.findViewById<androidx.cardview.widget.CardView>(R.id.cvIconContainer)?.setCardBackgroundColor(
            requireContext().getColor(category.colorResId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        launchAndCollectIn(viewModel.colors) { colors ->
            val adapter = ColorAdapter(colors) { selectedColor ->
                val category = viewModel.selectedCategory.value
                if (category != null) {
                    val updatedCategory = category.copy(colorResId = selectedColor)
                    viewModel.updateCategoryColor(updatedCategory)

                    updateCircleColor(selectedColor)
                }
            }

            binding.recyclerViewColors.apply {
                layoutManager = GridLayoutManager(requireContext(), 5)
                this.adapter = adapter
            }
        }
    }

    private fun updateCircleColor(colorResId: Int) {
        val color = requireContext().getColor(colorResId)
        binding.cardCategory.findViewById<androidx.cardview.widget.CardView>(R.id.cvIconContainer)?.setCardBackgroundColor(color)
    }

    private fun setupSaveButton() {
        binding.btnApplyChanges.setOnClickListener {
            val updatedCategory = viewModel.selectedCategory.value
            if (updatedCategory != null) {
                val action =
                    ChangeCategorySettingsFragmentDirections.actionChangeCategorySettingsFragmentToFirstSettingsFragment(
                        updatedCategory = updatedCategory
                    )
                findNavController().navigate(action)
            }
        }
    }
}
