package com.example.bodybilder.data.model

import com.example.bodybilder.data.local.entity.BodyPartEntity

data class BodyPart(
    val name: String,
    val exerciseCount: Int,
    val imageResId: Int
)

fun BodyPartEntity.toBodyPart(): BodyPart {
    return BodyPart(
        name = name,
        exerciseCount = exerciseCount,
        imageResId = imageResId
    )
}

fun BodyPart.toEntity(): BodyPartEntity {
    return BodyPartEntity(
        id = 0, // Room خودش id تولید می‌کنه
        name = name,
        exerciseCount = exerciseCount,
        imageResId = imageResId
    )
}