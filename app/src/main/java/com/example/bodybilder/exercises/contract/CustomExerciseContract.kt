package com.example.bodybilder.exercises.contract

import com.example.bodybilder.data.local.entity.BodyPartEntity
import com.example.bodybilder.data.model.Exercises


interface CustomExerciseContract {
    interface View {
        fun showBodyParts(bodyParts: List<BodyPartEntity>)
        fun showSuccess()
        fun showError(message: String)
    }

    interface Presenter {
        fun loadBodyParts()
        fun saveExercise(exercise: Exercises, bodyPartId: Int)
    }
}