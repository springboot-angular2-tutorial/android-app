package com.hana053.micropost.service

interface HttpErrorHandler {

    fun handleError(throwable: Throwable)

}
