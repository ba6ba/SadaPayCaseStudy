package com.ba6ba.sadapaycasestudy.home.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ba6ba.sadapaycasestudy.HomeActivity
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.managers.Constants
import com.ba6ba.sadapaycasestudy.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun isFragmentVisible() {
        launchFragmentInHiltContainer<HomeFragment>()
        onView(withId(R.id.fragment_root))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun onLaunchLoadingDisplayedWithDayNightIconElseGone(): Unit = runBlocking {
        launchFragmentInHiltContainer<HomeFragment>()
        onView(withId(R.id.items_recycler_view))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        onView(withId(R.id.loading_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.day_night_icon))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.failureLayout))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun onLaunchLoadingHideWithDayNightIconListShown(): Unit = runBlocking {
        launchFragmentInHiltContainer<HomeFragment>()
        delay(Constants.TEST_DELAY)
        onView(withId(R.id.items_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.failureLayout))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun clickOnDayNightIcon() {
        launchFragmentInHiltContainer<HomeFragment>()
        onView(withId(R.id.day_night_icon)).perform(click())
    }
}