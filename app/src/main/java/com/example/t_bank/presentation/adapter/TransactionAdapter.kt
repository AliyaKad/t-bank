package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.R
import com.example.t_bank.presentation.model.Transaction
import com.example.t_bank.presentation.viewModel.TransactionViewModel
import com.example.t_bank.presentation.model.CategoryRepository

class TransactionAdapter(
    private val viewModel: TransactionViewModel,
    private val onItemClickListener: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TransactionViewHolder(
        itemView: View,
        private val viewModel: TransactionViewModel
    ) : RecyclerView.ViewHolder(itemView) {

        private val textViewDescription = itemView.findViewById<TextView>(R.id.textViewDescription)
        private val textViewAmount = itemView.findViewById<TextView>(R.id.textViewAmount)
        private val textViewDate = itemView.findViewById<TextView>(R.id.textViewDate)
        private val buttonChoose = itemView.findViewById<Button>(R.id.buttonChooseCategory)
        private val recyclerViewCategories = itemView.findViewById<RecyclerView>(R.id.recyclerViewCategories)
        private val buttonSave = itemView.findViewById<Button>(R.id.buttonSaveCategory)

        private lateinit var categoryAdapter: CategoryForTransactionAdapter

        fun bind(transaction: Transaction) {
            textViewDescription.text = transaction.description
            textViewAmount.text = "${transaction.amount} ₽"
            textViewDate.text = transaction.date

            if (recyclerViewCategories.adapter == null) {
                categoryAdapter = CategoryForTransactionAdapter().apply {
                    submitList(CategoryRepository.categories)
                    onCategorySelected = { categoryId ->
                        val updated = transaction.copy(categoryId = categoryId)
                        viewModel.assignCategoryToTransaction(updated, categoryId)
                        notifyItemChanged(bindingAdapterPosition)
                    }
                }
                recyclerViewCategories.adapter = categoryAdapter
                recyclerViewCategories.layoutManager = LinearLayoutManager(itemView.context)
            }
            itemView.setOnClickListener {
                onItemClickListener(transaction)
            }

            buttonChoose.setOnClickListener {
                transaction.isExpanded = !transaction.isExpanded
                notifyItemChanged(adapterPosition)
            }

            buttonSave.setOnClickListener {
                val selectedId = categoryAdapter.selectedCategoryId
                if (selectedId != null) {
                    viewModel.assignCategoryToTransaction(transaction, selectedId)
                    transaction.isExpanded = false
                    notifyItemChanged(adapterPosition)
                }
            }

            recyclerViewCategories.visibility = if (transaction.isExpanded) View.VISIBLE else View.GONE
            buttonSave.visibility = if (transaction.isExpanded) View.VISIBLE else View.GONE
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem.description == newItem.description && oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem == newItem
    }
}