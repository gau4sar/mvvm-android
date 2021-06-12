package com.gaurav.fretello.utils

import com.gaurav.fretello.data.model.Exercise
import com.gaurav.fretello.data.model.Session
import com.gaurav.fretello.utils.Constants.HUNDRED_PERCENT
import timber.log.Timber
import kotlin.math.roundToInt

object MathUtil {

    /**
     * Calculates and returns the max percentage increase among all the sessions
     */
    fun getMaxPercentageIncreaseInteger(sessionListResponse: List<Session>): Int {
        val percentageIncrease = mutableListOf<Int>()

        sessionListResponse.forEach { sessions ->
            percentageIncrease.add(calculateIntegerPercentageIncrease(sessions))
        }

        return percentageIncrease.maxOrNull() ?: 0
    }

    /**
     * Calculates and returns the percentage increase (in Integer) of
     * next session's exercises to the average of the previous session's BPM
     */

    private val avgSessionBpmList = mutableListOf<Float>()

    fun calculateIntegerPercentageIncrease(session: Session): Int {

        var integerPercentageIncrease = 0

        avgSessionBpmList.add(calculateAverageSessionBpm(session.exercises))
        Timber.d("Average Bpm list : $avgSessionBpmList")

        if (avgSessionBpmList.size > 1) {

            // Iterate the next session and find the max BPM from all the exercises
            var maxBpmInNextSession = 0

            session.exercises.forEach { exercise ->
                maxBpmInNextSession =
                    if (exercise.practicedAtBpm > maxBpmInNextSession) exercise.practicedAtBpm else maxBpmInNextSession
            }
            Timber.d("Max BPM : $maxBpmInNextSession")

            // If we have more than 1 session then we calculate the percentage increase of the exercises
            val prevSessionAvgBpm = avgSessionBpmList[avgSessionBpmList.size - 2]
            Timber.d("Previous Average BPM : $prevSessionAvgBpm")

            integerPercentageIncrease =
                HUNDRED_PERCENT + ((maxBpmInNextSession - prevSessionAvgBpm) * 100 / prevSessionAvgBpm).roundToInt()
        }

        Timber.d("Integer Percentage Increase : $integerPercentageIncrease")
        return integerPercentageIncrease
    }

    /**
     * Calculates and returns the average BPM of all the exercises in a session
     */

    fun calculateAverageSessionBpm(exercises: List<Exercise>): Float {

        var bpmTotalSum = 0.0f

        exercises.forEach { exercise ->
            bpmTotalSum += exercise.practicedAtBpm
        }

        return bpmTotalSum / exercises.size
    }
}

