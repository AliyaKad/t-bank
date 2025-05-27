package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.R
import com.example.t_bank.presentation.model.CategoryForTransaction

class CategoryForTransactionAdapter :
    ListAdapter<CategoryForTransaction, CategoryForTransactionAdapter.CategoryViewHolder>(DiffCallback()) {

    var selectedCategoryId: Int? = null
        private set

    var onCategorySelected: ((Int) -> Unit)? = null

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val radioButton = itemView.findViewById<RadioButton>(R.id.radioButton)

        fun bind(category: CategoryForTransaction) {
            radioButton.text = category.name
            radioButton.isChecked = selectedCategoryId == category.id

            radioButton.setOnClickListener {
                selectedCategoryId = category.id
                notifyDataSetChanged()
                onCategorySelected?.invoke(category.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_for_transaction, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CategoryForTransaction>() {
        override fun areItemsTheSame(oldItem: CategoryForTransaction, newItem: CategoryForTransaction) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CategoryForTransaction, newItem: CategoryForTransaction) =
            oldItem == newItem
    }
}