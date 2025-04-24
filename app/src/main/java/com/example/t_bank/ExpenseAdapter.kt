package com.example.t_bank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.textViewCategoryName)
        val amount: TextView = itemView.findViewById(R.id.textViewAmount)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val progressDetailsLeft: TextView = itemView.findViewById(R.id.textViewProgressDetailsLeft)
        val progressDetailsRight: TextView = itemView.findViewById(R.id.textViewProgressDetailsRight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]

        holder.categoryName.text = expense.category

        holder.amount.text = "${expense.totalAmount.toInt()} ₽"

        val progress = if (expense.totalAmount > 0) {
            ((expense.spentAmount / expense.totalAmount) * 100).toInt()
        } else {
            0
        }
        holder.progressBar.progress = progress

        holder.progressDetailsLeft.text = "${expense.spentAmount.toInt()} ₽ потрачено"
        holder.progressDetailsRight.text = "${expense.totalAmount.toInt() - expense.spentAmount.toInt()} ₽ осталось"

    }

    override fun getItemCount(): Int {
        return expenses.size
    }
}