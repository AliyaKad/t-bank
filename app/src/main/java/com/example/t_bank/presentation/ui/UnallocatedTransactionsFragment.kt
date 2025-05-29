package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t_bank.databinding.FragmentUnallocatedBinding
import com.example.t_bank.presentation.adapter.TransactionAdapter
import com.example.t_bank.presentation.viewModel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class UnallocatedTransactionsFragment : Fragment() {

    private lateinit var adapter: TransactionAdapter
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUnallocatedBinding.inflate(inflater, container, false)

        adapter = TransactionAdapter(viewModel) {}
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.unallocatedTransactions.observe(viewLifecycleOwner) { transactions ->
            Log.d("UnallocatedFrag", "Received ${transactions.size} unallocated transactions: $transactions")
            adapter.submitList(transactions)
        }

        return binding.root
    }
}