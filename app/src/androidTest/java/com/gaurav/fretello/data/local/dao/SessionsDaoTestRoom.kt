package com.gaurav.fretello.data.local.dao

import android.content.Context
import android.graphics.BitmapFactory
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.gaurav.fretello.R
import com.gaurav.fretello.data.local.RoomDbTest
import com.gaurav.fretello.data.local.getOrAwaitValue
import com.gaurav.fretello.data.model.Exercise
import com.gaurav.fretello.data.model.Session
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class SessionsDaoTestRoom : RoomDbTest() {

    // Since live-data is asynchronous and
    // we want to execute all of the code simultaneously i.e. on the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var session: Session
    private val exercises = mutableListOf<Exercise>()

    private lateinit var dao: SessionsDao


    @Before
    fun setup() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_app_logo_round)
        Timber.d("Bitmap image : $bitmap")

        exercises.addAll(
            0,
            listOf(
                Exercise(
                    "0",
                    "Exercise 0",
                    97,
                    bitmap
                ),
                Exercise(
                    "1",
                    "Exercise 1",
                    101,
                    bitmap
                )
            )
        )

        session = Session(exercises, "Test Session", "2016-05-08T14:48:28.257Z")

        dao = db.getSessionsDao()
    }


    @Test
    fun insertSession() = runBlockingTest {
        dao.insertSession(session)

        val sessionList = dao.getSessionList().getOrAwaitValue()
        assertThat(sessionList).contains(session)
    }

    @Test
    fun insertAllSession() = runBlockingTest {
        val sessionList = listOf(session, session)
        dao.insertAllSessions(sessionList)

        val observeSessionList = dao.getSessionList().getOrAwaitValue()
        assertThat(observeSessionList).contains(session)
    }

}