package com.hana053.micropost.presentation.micropostnew;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.MicropostInteractor;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MicropostNewServiceTest extends RobolectricBaseTest {

    private MicropostInteractor micropostInteractor;
    private MicropostNewService micropostNewService;

    @Before
    public void setup() {
        micropostInteractor = getAppComponent().micropostInteractor();
        micropostNewService = new MicropostNewService(micropostInteractor);
    }

    @Test
    public void shouldCreatePost() {
        Observable<Micropost> response = Observable.just(new Micropost(1, "", 0, null));
        when(micropostInteractor.create(any(Micropost.class))).thenReturn(response);

        TestSubscriber<Micropost> testSubscriber = new TestSubscriber<>();
        micropostNewService.createPost("test content").subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
    }
}