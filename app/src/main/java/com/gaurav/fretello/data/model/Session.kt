package com.gaurav.fretello.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var exerciseList: List<Exercise> = listOf(),
    var name: String,
    var practicedOnDate: String,
    var isExpanded: Boolean = true
) : Serializable