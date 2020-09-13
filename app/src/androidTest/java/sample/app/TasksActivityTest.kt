/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.app

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sample.app.data.source.TasksRepository
import sample.app.tasks.TasksActivity
import sample.app.util.DataBindingIdlingResource
import sample.app.util.EspressoIdlingResource
import sample.app.util.deleteAllTasksBlocking
import sample.app.util.monitorActivity

/**
 * Large End-to-End test for the tasks module.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class TasksActivityTest {

    private lateinit var repository: TasksRepository

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    /**
     * Sets up Dagger components for testing.
     */
    @get:Rule
    val rule = DaggerTestApplicationRule()

    /**
     * Gets a reference to the [TasksRepository] exposed by the [DaggerTestApplicationRule].
     */
    @Before
    fun setupDaggerComponent() {
        repository = rule.component.tasksRepository
        repository.deleteAllTasksBlocking()
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun createTask() {
        // start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click on the "+" button, add details, and save
//        onView(withId(R.id.fab_add_task)).perform(click())
        onView(withId(R.id.add_task_title)).perform(typeText("title"), closeSoftKeyboard())
        onView(withId(R.id.add_task_description)).perform(typeText("description"))
        onView(withId(R.id.fab_save_task)).perform(click())

        // Then verify task is displayed on screen
        onView(withText("title")).check(matches(isDisplayed()))
    }
}
