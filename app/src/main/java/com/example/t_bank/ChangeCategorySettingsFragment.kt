package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.t_bank.adapter.ColorAdapter
import com.example.t_bank.databinding.FragmentChangeCategorySettingsBinding

class ChangeCategorySettingsFragment : Fragment() {

    private var _binding: FragmentChangeCategorySettingsBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeCategorySettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
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
        }

        binding.recyclerViewColors.apply {
            layoutManager = GridLayoutManager(requireContext(), 5)
            this.adapter = adapter
        }
    }
}
