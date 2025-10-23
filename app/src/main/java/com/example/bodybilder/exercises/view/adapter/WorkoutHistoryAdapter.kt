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
import java.util.Locale

class WorkoutHistoryAdapter : ListAdapter<WorkoutHistoryEntity, WorkoutHistoryAdapter.ViewHolder>(DiffCallback()) {

    // آرایه ماه‌ها به فارسی (بر اساس Locale ایران)
    private val persianMonths = arrayOf(
        "ژانویه", "فوریه", "مارس", "آوریل", "مه", "ژوئن",
        "ژوئیه", "اوت", "سپتامبر", "اکتبر", "نوامبر", "دسامبر"
    )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayText: TextView = view.findViewById(R.id.tv_day)  // روز
        val monthText: TextView = view.findViewById(R.id.tv_month)  // ماه (اسم)
        val yearText: TextView = view.findViewById(R.id.tv_year)  // سال
        val repsText: TextView = view.findViewById(R.id.tv_reps)
        val weightsText: TextView = view.findViewById(R.id.tv_weights)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show_reps_weights, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.repsText.text = "${item.reps} تکرار"
        holder.weightsText.text = "${item.weights} کیلو"

        // استخراج و تنظیم تاریخ (فرمت YYYY-MM-DD)
        val date = item.date
        val year = date.substring(0, 4)
        val monthNum = date.substring(5, 7).toIntOrNull() ?: 1  // ماه عدد (1-12)
        val day = date.substring(8, 10)

        // تبدیل ماه عدد به اسم فارسی
        val monthName = if (monthNum in 1..12) {
            persianMonths[monthNum - 1]
        } else {
            "نامشخص"
        }

        holder.yearText.text = year
        holder.monthText.text = monthName
        holder.dayText.text = day
    }

    class DiffCallback : DiffUtil.ItemCallback<WorkoutHistoryEntity>() {
        override fun areItemsTheSame(oldItem: WorkoutHistoryEntity, newItem: WorkoutHistoryEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: WorkoutHistoryEntity, newItem: WorkoutHistoryEntity): Boolean = oldItem == newItem
    }
}