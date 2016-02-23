package com.hana053.micropost.presentation.micropostnew;

import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;

import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.testing.RobolectricBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MicropostNewFragmentTest extends RobolectricBaseTest {

    private TestActivity activity;
    private MicropostNewFragment fragment;

    private Button postBtn;
    private EditText postEditText;

    @Before
    public void setup() {
        fragment = new MicropostNewFragment();
        SupportFragmentTestUtil.startFragment(fragment, TestActivity.class);
        activity = (TestActivity) fragment.getActivity();

        postBtn = fragment.getBinding().postBtn;
        postEditText = fragment.getBinding().postEditText;

        fragment.getBinding().executePendingBindings();
    }

    @Test
    public void shouldEnablePostBtnWhenContentWasInputted() {
        assertThat(postBtn.isEnabled(), is(false));
        postEditText.setText("a");
        assertThat(postBtn.isEnabled(), is(true));
    }

    @Test
    public void shouldPostContentWhenClickedPostBtn() {
        fragment.micropostNewService = spy(fragment.micropostNewService);
        postEditText.setText("test");
        postBtn.performClick();
        verify(fragment.micropostNewService).createPost("test");
    }

    @Test
    public void shouldFinishCtrlWhenClickedPostBtn() {
        postEditText.setText("test");
        postBtn.performClick();
        advance();
        verify(activity.micropostNewCtrl).finishWithPost();
    }

    private static class TestActivity extends FragmentActivity implements HasComponent<MicropostNewComponent> {
        private final MicropostNewCtrl micropostNewCtrl = mock(MicropostNewCtrl.class);

        @Override
        public MicropostNewComponent getComponent() {
            return DaggerMicropostNewComponent.builder()
                    .appComponent(BaseApplication.component(this))
                    .activityModule(new ActivityModule(this))
                    .micropostNewModule(new MicropostNewModule(micropostNewCtrl))
                    .build();
        }
    }

}
