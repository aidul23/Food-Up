package com.duodevloopers.foodup

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.duodevloopers.foodup.Activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.matchers.JUnitMatchers.containsString
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleTest {

    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSomething() {

        // finds a view with specified id and type something
        onView(withId(R.id.search)).perform(typeText("hello"))

        // finds a button with specified id and performs click
        onView(withId(R.id.button)).perform(click())


        // finds a text view with specified id with a text any text
        onView(withId(R.id.textView)).check(
            matches(withText(containsString("any text")))
        )
    }

}