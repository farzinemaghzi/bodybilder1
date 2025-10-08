package com.example.bodybilder.exercises.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bodybilder.R
import com.example.bodybilder.data.model.BodyPart
class BodyPartsAdapter(
    private var bodyParts: List<BodyPart>,
    private val onItemClick: (BodyPart) -> Unit
) : RecyclerView.Adapter<BodyPartsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.text_body_part)
        val exerciseCountTextView: TextView = itemView.findViewById(R.id.number_body_part_exercise)
        val imageView: ImageView = itemView.findViewById(R.id.image_body_part)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_body_part, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bodyPart = bodyParts[position]
        holder.nameTextView.text = bodyPart.name
        holder.exerciseCountTextView.text = "تمرین‌ها: ${bodyPart.exerciseCount}"
        holder.imageView.setImageResource(bodyPart.imageResId)
        holder.itemView.setOnClickListener { onItemClick(bodyPart) }
    }

    override fun getItemCount(): Int = bodyParts.size

    fun updateData(newBodyParts: List<BodyPart>) {
        bodyParts = newBodyParts
        notifyDataSetChanged()
    }
}