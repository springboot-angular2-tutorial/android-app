package com.hana053.micropost.testing;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class EmptyResponseBody extends ResponseBody {
    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return null;
    }
}
