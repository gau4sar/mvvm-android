package com.gaurav.fretello.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.gaurav.fretello.data.model.Exercise
import com.gaurav.fretello.data.model.Session

data class SessionWithExercises(
    @Embedded val session: Session,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val exercises: Exercises
)
