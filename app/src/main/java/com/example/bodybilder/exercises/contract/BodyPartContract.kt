package com.example.bodybilder.exercises.contract

import com.example.bodybilder.data.model.BodyPart // مدل BodyPart رو وارد کنید

interface BodyPartContract {
    interface View {
        fun showBodyParts(bodyParts: List<BodyPart>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadBodyParts()
        fun onBodyPartSelected(bodyPart: BodyPart)
        fun onDestroy()
    }

    interface Repository {
        suspend fun getBodyParts(): List<BodyPart>
        suspend fun insertInitialData()
    }
}