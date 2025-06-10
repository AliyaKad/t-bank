package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.R
import com.example.t_bank.databinding.ItemDistributionCategoryBinding
import com.example.t_bank.presentation.model.Category


class DistributionCategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<DistributionCategoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemDistributionCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.ivIcon.setImageResource(category.iconResId)
            binding.tvCategoryName.text = category.name
            binding.tvPercentage.text = binding.root.context.getString(
                R.string.percentage_format, category.percentage.toInt()
            )

            binding.cvIconContainer.setCardBackgroundColor(
                binding.root.context.getColor(category.colorResId)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDistributionCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}

