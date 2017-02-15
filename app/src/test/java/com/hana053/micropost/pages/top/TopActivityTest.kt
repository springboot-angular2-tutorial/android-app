package com.hana053.micropost.pages.top

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

//    // TODO remove later
//    val httpErrorHandler: HttpErrorHandler = mock(HttpErrorHandler::class.java)
//    val topPresenter: TopPresenter = mock(TopPresenter::class.java)

    @Before
    fun setup() {
//        overrideAppBindings {
//            bind<HttpErrorHandler>(overrides = true) with instance(httpErrorHandler)
//        }
//        putOverridingModule(TopActivity::class.java, Kodein.Module {
//            bind<TopPresenter>(overrides = true) with instance(topPresenter)
//        })
        activityController = Robolectric.buildActivity(TopActivity::class.java)
        activity = activityController.setup().get()

    }

    @Test
    fun shouldBeCreated() {
//        assertThat(httpErrorHandler, `is`(activity.httpErrorHandler))
//        assertThat(topPresenter, `is`(activity.presenter))
//        assertThat(navigator, `is`(activity.navigator))
        assertThat(activity, instanceOf(TopActivity::class.java))
    }

}