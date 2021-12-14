package com.example.pr13

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var database : SupernovaDatabase

    @Before
    @Throws(IOException::class)
    fun setup()
    {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, SupernovaDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun tearDown()
    {
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun testInsertData() {
        //database.supernovaDao().deleteAll()
        database.supernovaDao().insert(
            listOf(
                SupernovaDB(
                    "01.01.2021"
                )
            )
        )
        val s = database.supernovaDao().getAll()
        assertEquals(s.size, 1)
        if (s.isNotEmpty()) {
            assertEquals(s[0].name, "01.01.2021")
            //assertEquals(s[0].name, "Новый год")
        }
    }
    @Test
    fun testOutPutGetData() {

        database.supernovaDao().insert(
            listOf(
                SupernovaDB(
                    "01.01.2021",
                    //"Новый год"
                )
            )
        )
        database.supernovaDao().clear()
        val s = database.supernovaDao().getAll()
        assertEquals(s.size, 0)
    }
}