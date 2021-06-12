package com.gaurav.fretello.data.local

import androidx.room.TypeConverter
import com.gaurav.fretello.data.model.Exercise
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverters {

    @TypeConverter
    fun exerciseListToJson(exercises: List<Exercise>): String? {
        val type = object : TypeToken<List<Exercise>>() {}.type
        return Gson().toJson(exercises, type)
    }

    @TypeConverter
    fun exerciseJsonToList(exerciseString: String): List<Exercise> {
        val type = object : TypeToken<List<Exercise>>() {}.type
        return Gson().fromJson(exerciseString, type)
    }
}