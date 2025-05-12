package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.databinding.ItemCategoryBinding

import android.util.Log

class FirstSettingsAdapter(
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<FirstSettingsAdapter.ViewHolder>() {

    private var categories: List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("FirstSettingsAdapter", "Creating ViewHolder...")
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("FirstSettingsAdapter", "Binding data at position $position")
        holder.bind(categories[position], onItemClick)
    }

    override fun getItemCount(): Int {
        Log.d("FirstSettingsAdapter", "getItemCount called, returning ${categories.size}")
        return categories.size
    }

    fun submitList(newCategories: List<Category>) {
        Log.d("FirstSettingsAdapter", "Submitting new categories to adapter: $newCategories")
        categories = newCategories
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, onItemClick: (Category) -> Unit) {
            Log.d("FirstSettingsAdapter", "Binding category: $category")
            binding.ivIcon.setImageResource(category.iconResId)
            binding.tvCategoryName.text = category.name

            itemView.setOnClickListener {
                Log.d("FirstSettingsAdapter", "Item clicked: $category")
                onItemClick(category)
            }
        }
    }
}
