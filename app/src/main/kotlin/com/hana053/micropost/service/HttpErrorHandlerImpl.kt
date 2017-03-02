package com.hana053.micropost.service

import android.content.Context
import android.widget.Toast
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException

internal class HttpErrorHandlerImpl(
    private val authService: AuthService,
    context: Context
) : HttpErrorHandler {

    private val context: Context = context.applicationContext

    override fun handleError(throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> showToast("Connection timed out.")
            is ConnectException -> showToast("Cannot connect to server.")
            is HttpException -> {
                when {
                    (throwable.code() == 401) -> {
                        showToast("Please sign in.")
                        authService.logout()
                    }
                    (throwable.code() >= 500) -> showToast("Something bad happened.")
                }
            }
            is Throwable -> {
                Timber.e(throwable, "handleHttpError: ${throwable.message}")
                showToast("Something bad happened.")
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

}
