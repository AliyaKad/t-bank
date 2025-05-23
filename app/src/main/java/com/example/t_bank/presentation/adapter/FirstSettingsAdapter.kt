package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.databinding.ItemCategoryBinding

class FirstSettingsAdapter(
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<FirstSettingsAdapter.ViewHolder>() {

    private var categories: List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun submitList(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, onItemClick: (Category) -> Unit) {

            binding.ivIcon.setImageResource(category.iconResId)

            binding.tvCategoryName.text = category.name

            binding.cvIconContainer.setCardBackgroundColor(itemView.context.getColor(category.colorResId))

            itemView.setOnClickListener {
                onItemClick(category)
            }
        }
    }
}
