package com.hana053.micropost.presentation.core.components.avatar;

import com.hana053.micropost.domain.User;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AvatarViewModelTest {

    private final User user = new User(1, "", "test@tes.com", "asdfghl12");
    private final AvatarViewModel viewModel = new AvatarViewModel(user);

    @Test
    public void shouldGenerateAvatarUrl() {
        assertThat(viewModel.getUrl(5), is("https://secure.gravatar.com/avatar/asdfghl12?s=5"));
    }
}