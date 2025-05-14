package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.presentation.model.Expense
import com.example.t_bank.R
import com.example.t_bank.databinding.ItemExpenseBinding

class ExpenseAdapter(private val expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(private val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {
            binding.textViewCategoryName.text = expense.category

            binding.textViewAmount.text = itemView.context.getString(
                R.string.amount_format,
                expense.totalAmount.toInt()
            )

            val progress = if (expense.totalAmount > 0) {
                ((expense.spentAmount / expense.totalAmount) * 100).toInt()
            } else {
                0
            }
            binding.progressBar.progress = progress

            binding.textViewProgressDetailsLeft.text = itemView.context.getString(
                R.string.spent_amount_format,
                expense.spentAmount.toInt()
            )

            binding.textViewProgressDetailsRight.text = itemView.context.getString(
                R.string.remaining_amount_format,
                (expense.totalAmount - expense.spentAmount).toInt()
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    override fun getItemCount(): Int {
        return expenses.size
    }
}

