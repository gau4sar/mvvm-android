package com.gaurav.fretello.utils


import com.gaurav.fretello.data.model.Exercise
import com.gaurav.fretello.data.model.Session
import com.gaurav.fretello.data.remote.responses.GetSessionListResponse
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class MathUtilTest {

    private lateinit var session: Session
    private val exercises = mutableListOf<Exercise>()

    @Before
    fun setup() {
        exercises.addAll(
            0, listOf(
                Exercise("0", "Exercise 0", 97),
                Exercise("1", "Exercise 1", 101),
                Exercise("2", "Exercise 2", 69),
                Exercise("3", "Exercise 3", 100)
            )
        )

        session = Session(exercises, "Test Session", "2016-05-08T14:48:28.257Z")
    }

    @Test
    fun `check if getMaxPercentageIncreaseInteger returns 0 if list is empty`() {
        val result = MathUtil.getMaxPercentageIncreaseInteger(listOf())
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun calculateIntegerPercentageIncrease() {
        val result = MathUtil.calculateIntegerPercentageIncrease(session)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun calculateAverageSessionBPM() {
        val result = MathUtil.calculateAverageSessionBpm(exercises)
        assertThat(result).isEqualTo(91.75f)
    }

}