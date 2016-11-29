package com.hana053.micropost.ui.components.micropostlist;

import com.hana053.micropost.domain.Micropost;
import com.hana053.micropost.domain.User;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PostItemViewModelTest {

    private PostItemViewModel viewModel;

    @Before
    public void setup() {
        final User user = new User(200, "test user", "test@test.com", "");
        final Micropost micropost = new Micropost(100, "content", 0, user);
        viewModel = new PostItemViewModel(micropost);
    }

    @Test
    public void shouldGetUserName() {
        assertThat(viewModel.getUserName(), is("test user"));
    }

    @Test
    public void shouldGetContent() {
        assertThat(viewModel.getContent(), is("content"));
    }
}
