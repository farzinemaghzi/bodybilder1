package com.example.bodybilder.exercises.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bodybilder.R

class DescriptionStepAdapter (
    private val stepImages: List<Int>  // **fix: List<Int> به جای String – match با pass**
) : RecyclerView.Adapter<DescriptionStepAdapter.ViewHolder>() {  // **fix: RecyclerView.Adapter برای RecyclerView**
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_step_pager_dis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_step_pager_dis, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageResId = stepImages[position]  // **fix: از List<Int> استفاده کن**
        holder.imageView.setImageResource(imageResId)
    }

    override fun getItemCount(): Int = stepImages.size
}