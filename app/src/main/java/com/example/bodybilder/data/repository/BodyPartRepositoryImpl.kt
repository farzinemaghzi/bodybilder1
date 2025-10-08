package com.example.bodybuilder.data.repository

import android.content.Context
import com.example.bodybilder.R
import com.example.bodybilder.data.local.DatabaseProvider
import com.example.bodybilder.data.local.entity.BodyPartEntity
import com.example.bodybilder.data.model.BodyPart
import com.example.bodybilder.data.model.toBodyPart
import com.example.bodybilder.exercises.contract.BodyPartContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BodyPartRepositoryImpl(private val context: Context) : BodyPartContract.Repository {
    private val bodyPartDao = DatabaseProvider.getDatabase(context).bodyPartDao()

    override suspend fun getBodyParts(): List<BodyPart> {
        return withContext(Dispatchers.IO) {
            bodyPartDao.getAll().map { it.toBodyPart() }
        }
    }

    override suspend fun insertInitialData() {
        withContext(Dispatchers.IO) {
            if (bodyPartDao.getAll().isEmpty()) {
                val initialBodyParts = listOf(
                    BodyPartEntity(name = "Chest", exerciseCount = 5, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Chest1", exerciseCount = 5, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Chest2", exerciseCount = 5, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Chest3", exerciseCount = 5, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Chest4", exerciseCount = 5, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Chest5", exerciseCount = 5, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Chest6", exerciseCount = 5, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Back", exerciseCount = 4, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Legs", exerciseCount = 6, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Arms", exerciseCount = 3, imageResId = R.drawable.team_member_4),
                    BodyPartEntity(name = "Shoulders", exerciseCount = 4, imageResId = R.drawable.team_member_4)
                )
                initialBodyParts.forEach { bodyPartDao.insert(it) }
            }
        }
    }
}