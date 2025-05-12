package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var categories: List<Category>,
    var onCategoryUpdated: ((Category) -> Unit)? = null
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, onCategoryUpdated: ((Category) -> Unit)?) {
            binding.tvCategoryName.text = category.name
            binding.etPercentage.setText(category.percentage.toString())
            binding.ivIcon.setImageResource(category.iconResId)

            binding.etPercentage.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val newPercentage = binding.etPercentage.text.toString().toFloatOrNull() ?: category.percentage
                    val updatedCategory = category.copy(percentage = newPercentage)
                    onCategoryUpdated?.invoke(updatedCategory)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position], onCategoryUpdated)
    }

    override fun getItemCount(): Int = categories.size

    fun submitList(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    fun getCategories(): List<Category> {
        return categories
    }
}