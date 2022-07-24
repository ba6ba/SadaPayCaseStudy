package com.ba6ba.sadapaycasestudy

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun isActivityVisible() {
        Espresso.onView(withId(R.id.activity_root))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun isNavHostFragmentVisible() {
        Espresso.onView(
            Matchers.allOf(
                withId(R.id.navHostFragment),
                ViewMatchers.withParent(
                    withId(R.id.activity_root)
                ),
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun isStartDestinationAssigned() {
        val startDestinationId = R.id.homeFragment
        Espresso.onView(
            Matchers.allOf(
                withId(startDestinationId),
                ViewMatchers.withParent(withId(R.id.activity_root)),
                ViewMatchers.isDisplayed()
            )
        )
    }
}