package com.gaurav.fretello.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gaurav.fretello.data.model.Exercise

@Dao
interface ExercisesDao {

    @Query("SELECT * from exercises where exercises.id = :exercisesId")
    fun getExercises(exercisesId: Int?): LiveData<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: Exercise)

    @Query("DELETE FROM exercises")
    fun deleteAll()
}