package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.databinding.ItemColorBinding
import androidx.core.content.ContextCompat

class ColorAdapter(private val colors: List<Int>, private val onColorSelected: (Int) -> Unit) :
    RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(colors[position])
    }

    override fun getItemCount(): Int = colors.size

    inner class ViewHolder(private val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(colorResId: Int) {
            val color = ContextCompat.getColor(binding.root.context, colorResId)

            binding.colorView.setCardBackgroundColor(color)

            binding.colorView.setOnClickListener {
                onColorSelected(color)
            }
        }
    }
}
