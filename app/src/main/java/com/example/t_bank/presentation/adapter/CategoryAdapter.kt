package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
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
            binding.ivIcon.setImageResource(category.iconResId)
            binding.cvIconContainer.setCardBackgroundColor(itemView.context.getColor(category.colorResId))

            binding.seekBarPercentage.progress = category.percentage.toInt()
            binding.tvPercentage.text = "${category.percentage}%"

            binding.seekBarPercentage.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        val newPercentage = progress.toFloat()

                        val currentCategories = (bindingAdapter as? CategoryAdapter)?.getCategories() ?: emptyList()
                        val totalPercentage = currentCategories.sumOf { it.percentage.toDouble() } - category.percentage + newPercentage

                        if (totalPercentage <= 100.0) {
                            binding.tvPercentage.text = "$newPercentage%"
                        } else {
                            seekBar?.progress = (100 - (currentCategories.sumOf { it.percentage.toDouble() } - category.percentage)).toInt()
                            binding.tvPercentage.text = "${seekBar?.progress}%"
                        }
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    val newPercentage = seekBar?.progress?.toFloat() ?: category.percentage
                    val updatedCategory = category.copy(percentage = newPercentage)
                    onCategoryUpdated?.invoke(updatedCategory)
                }
            })
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
