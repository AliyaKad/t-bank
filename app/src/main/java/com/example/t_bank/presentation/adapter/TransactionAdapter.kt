package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.R
import com.example.t_bank.presentation.model.Transaction

class TransactionAdapter(private var transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        private val tvAmount = itemView.findViewById<TextView>(R.id.tvAmount)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)

        fun bind(transaction: Transaction) {
            tvDescription.text = transaction.description
            tvAmount.text = "${transaction.amount} ₽"
            tvDate.text = transaction.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    fun submitList(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
}
