package com.example.pr13

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.*


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

fun test(supernovae:Supernova): Matcher<View> {

    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            description?.appendText("with supernova ${supernovae.name!![0]} ")
        }

        override fun matchesSafely(item: RecyclerView?): Boolean {
            val listStorage = (item?.adapter as SupernovaeAdapter).getList()
            val result = listStorage.any{it.name!![0] == supernovae.name!![0] }
            return result

        }

    }
}

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get: Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)
    @Test
    fun test1() {
        onView(withId(R.id.ra)).perform(ViewActions.typeText("21:23:32.16"))
        onView(withId(R.id.dec)).perform(ViewActions.typeText("-53:01:36.08"))
        onView(withId(R.id.radius)).perform(ViewActions.typeText("5000"))
        onView(withId(R.id.load)).perform(ViewActions.click())
        val supernovaStorage = Supernova(arrayOf("GRB 060614A"))
        Thread.sleep(3000)
        onView(withId(R.id.recycler_view_supernovae)).check(matches(test(supernovaStorage)))
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pr13", appContext.packageName)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testVisibilityRecyclerView() {
        Thread.sleep(1000)
        onView(ViewMatchers.withId(R.id.recycler_view_supernovae))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(
                        activityRule.activity.window.decorView
                    )
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

//    @Test
//    @Throws(InterruptedException::class)
//    fun testCaseForRecyclerScroll() {
//        Thread.sleep(1000)
//        val recyclerView =
//            activityRule.activity.findViewById<RecyclerView>(R.id.recyclerView)
//        val itemCount =
//            Objects.requireNonNull(recyclerView.adapter!!).itemCount
//        onView(ViewMatchers.withId(R.id.recyclerView))
//            .inRoot(
//                RootMatchers.withDecorView(
//                    Matchers.`is`(
//                        activityRule.activity.window.decorView
//                    )
//                )
//            )
//            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
//    }

//    @Test
//    @Throws(InterruptedException::class)
//    fun testCaseForRecyclerClick() {
//        Thread.sleep(1000)
//        onView(ViewMatchers.withId(R.id.recyclerView))
//            .inRoot(
//                RootMatchers.withDecorView(
//                    Matchers.`is`(
//                        activityRule.activity.window.decorView
//                    )
//                )
//            )
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                    0,
//                    ViewActions.click()
//                )
//            )
//    }


}