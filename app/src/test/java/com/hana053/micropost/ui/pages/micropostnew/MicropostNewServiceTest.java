package com.hana053.micropost.ui.pages.micropostnew;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.interactors.MicropostInteractor;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MicropostNewServiceTest extends RobolectricBaseTest {

    private final MicropostInteractor micropostInteractor = mock(MicropostInteractor.class);
    private final MicropostNewService micropostNewService = new MicropostNewServiceImpl(micropostInteractor);

    @Test
    public void shouldCreatePost() {
        Observable<Micropost> response = Observable.just(new Micropost(1, "", 0, null));
        when(micropostInteractor.create(any(Micropost.class))).thenReturn(response);

        TestSubscriber<Micropost> testSubscriber = new TestSubscriber<>();
        micropostNewService.createPost("test content").subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
    }
}