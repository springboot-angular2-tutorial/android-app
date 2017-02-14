package com.hana053.micropost.activity.pages.top

import com.hana053.micropost.pages.top.TopActivity
import com.hana053.micropost.testing.RobolectricBaseTest
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.util.ActivityController

class TopActivityTest : RobolectricBaseTest() {

    lateinit var activityController: ActivityController<TopActivity>
    lateinit var activity: TopActivity

    @Before
    fun setup() {
        activityController = Robolectric.buildActivity(TopActivity::class.java)
        activity = activityController.setup().get()
    }

    @Test
    fun shouldBeCreated() {
        assertThat(activity, instanceOf(TopActivity::class.java))
    }

}