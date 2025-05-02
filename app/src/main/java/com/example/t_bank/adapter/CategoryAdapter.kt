package com.example.t_bank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.Category
import com.example.t_bank.databinding.ItemCategoryBinding

class CategoryAdapter(private var categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var onCategoryUpdated: (() -> Unit)? = null

    class ViewHolder(
        private val binding: ItemCategoryBinding,
        private val adapter: CategoryAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.name
            binding.etPercentage.setText(category.percentage.toString())

            binding.etPercentage.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val newPercentage = binding.etPercentage.text.toString().toFloatOrNull() ?: 0f
                    category.percentage = newPercentage
                    adapter.onCategoryUpdated?.invoke()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun getCategories(): List<Category> {
        return categories
    }
}
