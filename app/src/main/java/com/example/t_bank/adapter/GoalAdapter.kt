package com.example.t_bank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.Goal
import com.example.t_bank.R
import com.example.t_bank.databinding.ItemGoalBinding

class GoalAdapter(private val goals: List<Goal>) :
    RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    class GoalViewHolder(private val binding: ItemGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(goal: Goal) {
            binding.textViewGoalName.text = goal.name

            binding.textViewAmount.text = "%s"
                goal.amount


            binding.textViewEndDate.text = itemView.context.getString(
                R.string.end_date_format,
                goal.endDate
            )

            binding.progressBar.progress = if (goal.isAchieved) 100 else 50

            if (goal.isAchieved) {
                binding.textViewStatus.text = itemView.context.getString(R.string.goal_achieved)
                binding.textViewStatus.setTextColor(itemView.context.getColor(R.color.green))
            } else {
                binding.textViewStatus.text = itemView.context.getString(R.string.goal_not_achieved)
                binding.textViewStatus.setTextColor(itemView.context.getColor(R.color.red))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding = ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(goals[position])
    }

    override fun getItemCount(): Int {
        return goals.size
    }
}
