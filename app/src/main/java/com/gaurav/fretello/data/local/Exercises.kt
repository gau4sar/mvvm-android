package com.gaurav.fretello.data.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gaurav.fretello.data.model.Exercise
import com.google.gson.annotations.Expose

@Entity(tableName = "exercises")
data class Exercises constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @Expose
    @NonNull
    var exercises: List<Exercise>
)
