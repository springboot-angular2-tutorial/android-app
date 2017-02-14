package com.hana053.micropost.pages.top

import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.hana053.micropost.services.HttpErrorHandler
import com.hana053.micropost.testing.RobolectricBaseTest
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.robolectric.Robolectric
import org.robolectric.util.ActivityController

class TopActivityTest : RobolectricBaseTest() {

    lateinit var activityController: ActivityController<TopActivity>
    lateinit var activity: TopActivity

    // TODO remove later
    val httpErrorHandler: HttpErrorHandler = mock(HttpErrorHandler::class.java)
//    val navigator: Navigator = mock(Navigator::class.java)

    @Before
    fun setup() {
        overrideBindings {
            bind<HttpErrorHandler>(overrides = true) with instance(httpErrorHandler)
        }
        activityController = Robolectric.buildActivity(TopActivity::class.java)
        activity = activityController.setup().get()

    }

    @Test
    fun shouldBeCreated() {
        assertThat(httpErrorHandler, `is`(activity.httpErrorHandler))
//        assertThat(navigator, `is`(activity.navigator))
        assertThat(activity, instanceOf(TopActivity::class.java))
    }

}