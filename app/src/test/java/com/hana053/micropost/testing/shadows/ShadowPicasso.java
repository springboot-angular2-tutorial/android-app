package com.hana053.micropost.testing.shadows;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Implements(Picasso.class)
public class ShadowPicasso {

    @Implementation
    public static Picasso with(Context context) {
        final Picasso picasso = mock(Picasso.class);
        when(picasso.load(any(String.class)))
                .thenReturn(mock(RequestCreator.class));
        return picasso;
    }

}
