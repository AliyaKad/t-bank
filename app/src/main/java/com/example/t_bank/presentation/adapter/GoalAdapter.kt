package com.example.t_bank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.t_bank.presentation.model.Goal
import com.example.t_bank.R
import com.example.t_bank.databinding.ItemGoalBinding
import android.util.Log


class GoalAdapter(
    private var goals: List<Goal>,
    private val onLongClick: (Goal) -> Unit,
    private val onItemClick: (Goal) -> Unit
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    class GoalViewHolder(private val binding: ItemGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            goal: Goal,
            onLongClick: (Goal) -> Unit,
            onItemClick: (Goal) -> Unit
        ) {

            Log.d("adapter", goal.id.toString())
            binding.textViewGoalName.text = goal.name

            binding.textViewAmount.text = binding.root.context.getString(
                R.string.amount_format_float,
                goal.amount
            )
            binding.textViewEndDate.text = binding.root.context.getString(
                R.string.end_date_format,
                goal.endDate
            )

            binding.progressBar.progress = goal.progress

            if (goal.isAchieved) {
                binding.textViewStatus.text = binding.root.context.getString(R.string.goal_achieved)
                binding.textViewStatus.setTextColor(binding.root.context.getColor(R.color.green))
            } else {
                binding.textViewStatus.text = binding.root.context.getString(R.string.goal_not_achieved)
                binding.textViewStatus.setTextColor(binding.root.context.getColor(R.color.red))
            }

            itemView.setOnClickListener { onItemClick(goal) }
            itemView.setOnLongClickListener {
                onLongClick(goal)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding = ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(goals[position], onLongClick, onItemClick)
    }

    override fun getItemCount(): Int {
        return goals.size
    }

    fun updateData(newGoals: List<Goal>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = goals.size
            override fun getNewListSize(): Int = newGoals.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return goals[oldItemPosition].id == newGoals[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return goals[oldItemPosition] == newGoals[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        goals = newGoals
        diffResult.dispatchUpdatesTo(this)
    }
}
