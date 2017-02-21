package com.hana053.micropost.testing

import okhttp3.ResponseBody

class EmptyResponseBody : ResponseBody() {

    override fun contentType() = null
    override fun contentLength(): Long = 0
    override fun source() = null

}
