package com.example.t_bank

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalAdapter(private val goals: List<Goal>) :
    RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val goalName: TextView = itemView.findViewById(R.id.textViewGoalName)
        val goalAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        val goalEndDate: TextView = itemView.findViewById(R.id.textViewEndDate)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val goalStatus: TextView = itemView.findViewById(R.id.textViewStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]

        holder.goalName.text = goal.name
        holder.goalAmount.text = "${goal.amount}"
        holder.goalEndDate.text = "Окончание: ${goal.endDate}"
        holder.progressBar.progress = if (goal.isAchieved) 100 else 50

        if (goal.isAchieved) {
            holder.goalStatus.text = "Цель достигнута!"
            holder.goalStatus.setTextColor(Color.GREEN)
        } else {
            holder.goalStatus.text = "Цель не достигнута"
            holder.goalStatus.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return goals.size
    }
}