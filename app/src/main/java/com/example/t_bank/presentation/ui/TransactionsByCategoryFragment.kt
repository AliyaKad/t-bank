package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.R
import com.example.t_bank.databinding.FragmentTransactionsByCategoryBinding
import com.example.t_bank.presentation.adapter.TransactionAdapter
import com.example.t_bank.presentation.viewModel.TransactionsByCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsByCategoryFragment : Fragment() {

    private var _binding: FragmentTransactionsByCategoryBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var viewModel: TransactionsByCategoryViewModel
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsByCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryName = arguments?.getString("categoryName")

        binding.categoryTitleTextView.text = categoryName

        viewModel = ViewModelProvider(this)[TransactionsByCategoryViewModel::class.java]

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.loadTransactionsByCategory(categoryName)
            adapter.submitList(viewModel.getFilteredTransactions())
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TransactionAdapter(viewModel.getFilteredTransactions())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
