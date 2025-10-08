package com.example.bodybilder.exercises.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bodybilder.R
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.exercises.view.CustomExerciseFragment

class ExercisesAdapter(
    private var exercises: List<Exercises>,
    private val onItemClick: (Exercises) -> Unit
) : RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.exercise_name)
        val repsTextView: TextView = itemView.findViewById(R.id.exercise_reps)
        val setsTextView: TextView = itemView.findViewById(R.id.exercise_sets)
        val imageView: ImageView = itemView.findViewById(R.id.exercise_image)
        val editButton: ImageButton = itemView.findViewById(R.id.button_edit_exercise)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercises, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.nameTextView.text = exercise.name
        holder.repsTextView.text = "تکرارها: ${exercise.reps}"
        holder.setsTextView.text = "ست‌ها: ${exercise.sets}"
        holder.imageView.setImageResource(exercise.imageResId)
        holder.itemView.setOnClickListener { onItemClick(exercise) }

        if (exercise.isCustom) {
            holder.editButton.visibility = View.VISIBLE
            holder.editButton.setOnClickListener {
                val fragment = CustomExerciseFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("exercise", exercise)
                    }
                }
                (holder.itemView.context as? FragmentActivity)?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        } else {
            holder.editButton.visibility = View.GONE
        }

        holder.itemView.setOnClickListener { onItemClick(exercise) }
    }

    override fun getItemCount(): Int = exercises.size

    fun updateData(newExercises: List<Exercises>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
}