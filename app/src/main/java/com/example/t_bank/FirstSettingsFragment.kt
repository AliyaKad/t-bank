package com.example.t_bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.adapter.FirstSettingsAdapter
import com.example.t_bank.databinding.FragmentFirstSettingsBinding

class FirstSettingsFragment : Fragment() {

    private var _binding: FragmentFirstSettingsBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstSettingsBinding.inflate(inflater, container, false)
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
        val categories = listOf(
            Category("Продукты", R.drawable.ic_food, 0f),
            Category("Транспорт", R.drawable.ic_transport, 0f),
            Category("Коммунальные услуги", R.drawable.ic_utilities, 0f),
            Category("Развлечения", R.drawable.ic_entertainment, 0f),
            Category("Другое", R.drawable.ic_other, 0f)
        )

        val adapter = FirstSettingsAdapter(categories)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }
}
