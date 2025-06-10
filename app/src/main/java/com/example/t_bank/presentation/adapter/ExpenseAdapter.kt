package com.example.t_bank.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.presentation.model.Expense
import com.example.t_bank.R
import com.example.t_bank.databinding.ItemExpenseBinding

class ExpenseAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private var items: List<Expense> = emptyList()

    fun submitList(list: List<Expense>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ExpenseViewHolder(
        private val binding: ItemExpenseBinding,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

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
            binding.progressBar.progressTintList = ColorStateList.valueOf(
                ContextCompat.getColor(itemView.context, expense.colorResId)
            )

            binding.textViewProgressDetailsRight.text = itemView.context.getString(
                R.string.remaining_amount_format,
                (expense.totalAmount - expense.spentAmount).toInt()
            )

            binding.root.setOnClickListener {
                onItemClick(expense.category)
            }
        }
    }
}
