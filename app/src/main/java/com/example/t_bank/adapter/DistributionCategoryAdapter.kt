package com.example.t_bank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.databinding.ItemDistributionCategoryBinding
import com.example.t_bank.Category
import com.example.t_bank.R

class DistributionCategoryAdapter(private var categories: List<Category>) :
    RecyclerView.Adapter<DistributionCategoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemDistributionCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.ivIcon.setImageResource(category.iconResId)
            binding.tvCategoryName.text = category.name
            binding.tvPercentage.text = binding.root.context.getString(R.string.percentage_format, category.percentage.toInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDistributionCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}

