package com.example.t_bank.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.t_bank.databinding.FragmentAllocatedBinding
import com.example.t_bank.presentation.adapter.TransactionAdapter
import com.example.t_bank.presentation.viewModel.TransactionViewModel

class AllocatedTransactionsFragment : Fragment() {

    private lateinit var adapter: TransactionAdapter
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAllocatedBinding.inflate(inflater, container, false)

        adapter = TransactionAdapter(viewModel) { transaction ->
        }

        binding.recyclerView.adapter = adapter

        viewModel.allocatedTransactions.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}