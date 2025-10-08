package com.example.bodybilder.exercises.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bodybilder.R
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity

class WorkoutHistoryAdapter : ListAdapter<WorkoutHistoryEntity, WorkoutHistoryAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.tv_date)
        val repsText: TextView = view.findViewById(R.id.tv_reps)
        val weightsText: TextView = view.findViewById(R.id.tv_weights)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show_reps_weights, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.dateText.text = item.date
        holder.repsText.text = "${item.reps} تکرار"
        holder.weightsText.text = "${item.weights} کیلو"
    }

    class DiffCallback : DiffUtil.ItemCallback<WorkoutHistoryEntity>() {
        override fun areItemsTheSame(oldItem: WorkoutHistoryEntity, newItem: WorkoutHistoryEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: WorkoutHistoryEntity, newItem: WorkoutHistoryEntity): Boolean = oldItem == newItem
    }
}