package com.hana053.micropost.services;

import com.hana053.micropost.testing.EmptyResponseBody;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowToast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class HttpErrorHandlerTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    @Mock
    private LoginService loginService;

    private HttpErrorHandler httpErrorHandler;

    @Before
    public void setup() {
        httpErrorHandler = new HttpErrorHandlerImpl(RuntimeEnvironment.application, loginService);
    }

    @Test
    public void shouldHandleSocketTimeoutException() {
        httpErrorHandler.handleError(new SocketTimeoutException());
        assertThat(ShadowToast.getTextOfLatestToast(), is("Connection timed out."));
    }

    @Test
    public void shouldHandleConnectException() {
        httpErrorHandler.handleError(new ConnectException());
        assertThat(ShadowToast.getTextOfLatestToast(), is("Cannot connect to server."));
    }

    @Test
    public void shouldHandle401Error() {
        httpErrorHandler.handleError(createHttpException(401));
        assertThat(ShadowToast.getTextOfLatestToast(), is("Please sign in."));
        verify(loginService).logout();
    }

    @Test
    public void shouldHandle500Error() {
        httpErrorHandler.handleError(createHttpException(500));
        assertThat(ShadowToast.getTextOfLatestToast(), is("Something bad happened."));
    }

    @Test
    public void shouldHandleUnknownError() {
        httpErrorHandler.handleError(new RuntimeException());
        assertThat(ShadowToast.getTextOfLatestToast(), is("Something bad happened."));
    }

    private HttpException createHttpException(int status) {
        return new HttpException(Response.error(status, new EmptyResponseBody()));
    }

}