package com.hana053.micropost.testing

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource

class EmptyResponseBody : ResponseBody() {

    override fun contentType(): MediaType? = null
    override fun contentLength(): Long = 0
    override fun source(): BufferedSource? = null

}
