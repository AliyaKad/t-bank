package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.presentation.model.Expense
import com.example.t_bank.R
import com.example.t_bank.databinding.ItemExpenseBinding

class SpendingAdapter(private var spendings: List<Expense>) :
    RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder>() {

    class SpendingViewHolder(private val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spending: Expense) {
            binding.textViewCategoryName.text = spending.category
            binding.textViewAmount.text = itemView.context.getString(
                R.string.amount_format,
                spending.totalAmount.toInt()
            )
            val progress = if (spending.totalAmount > 0) {
                ((spending.spentAmount / spending.totalAmount) * 100).toInt()
            } else {
                0
            }
            binding.progressBar.progress = progress
            binding.textViewProgressDetailsLeft.text = itemView.context.getString(
                R.string.spent_amount_format,
                spending.spentAmount.toInt()
            )

            binding.textViewProgressDetailsRight.text = itemView.context.getString(
                R.string.remaining_amount_format,
                (spending.totalAmount - spending.spentAmount).toInt()
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpendingViewHolder, position: Int) {
        holder.bind(spendings[position])
    }

    override fun getItemCount(): Int {
        return spendings.size
    }

    fun updateData(newSpendings: List<Expense>) {
        this.spendings = newSpendings
        notifyDataSetChanged()
    }
}
