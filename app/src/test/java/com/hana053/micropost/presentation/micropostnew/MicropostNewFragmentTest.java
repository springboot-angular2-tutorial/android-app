package com.hana053.micropost.presentation.micropostnew;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;

import com.hana053.micropost.presentation.core.base.BaseApplication;
import com.hana053.micropost.presentation.core.di.ActivityModule;
import com.hana053.micropost.presentation.core.di.HasComponent;
import com.hana053.micropost.testing.RobolectricBaseTest;
import com.hana053.micropost.testing.RobolectricDaggerMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import rx.Observable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressLint("SetTextI18n")
public class MicropostNewFragmentTest extends RobolectricBaseTest {

    @Rule
    public final RobolectricDaggerMockRule rule = new RobolectricDaggerMockRule();

    private Button postBtn;
    private EditText postEditText;

    @Mock
    private MicropostNewService micropostNewService;

    @Mock
    private MicropostNewCtrl micropostNewCtrl;

    @Before
    public void setup() {
        when(micropostNewService.createPost(anyString())).thenReturn(Observable.empty());

        MicropostNewFragment fragment = new MicropostNewFragment();
        SupportFragmentTestUtil.startFragment(fragment, TestActivity.class);

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
        postEditText.setText("test");
        postBtn.performClick();
        verify(micropostNewService).createPost("test");
    }

    @Test
    public void shouldFinishCtrlWhenClickedPostBtn() {
        postEditText.setText("test");
        postBtn.performClick();
        advance();
        verify(micropostNewCtrl).finishWithPost();
    }

    private static class TestActivity extends FragmentActivity implements MicropostNewCtrl, HasComponent<MicropostNewComponent> {
        @Override
        public MicropostNewComponent getComponent() {
            return BaseApplication.component(this)
                    .activityComponent(new ActivityModule(this))
                    .micropostNewComponent(new MicropostNewModule(this));
        }

        @Override
        public void finishWithPost() {
        }
    }

}
