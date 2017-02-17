package com.hana053.micropost.services

import android.content.Context
import android.widget.Toast
import retrofit2.adapter.rxjava.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException

internal class HttpErrorHandlerImpl(
    private val loginService: LoginService,
    context: Context
) : HttpErrorHandler {

    private val context: Context = context.applicationContext

    override fun handleError(throwable: Throwable) {
        try {
            throw throwable
        } catch (e: SocketTimeoutException) {
            Toast.makeText(context, "Connection timed out.", Toast.LENGTH_LONG).show()
        } catch (e: ConnectException) {
            Toast.makeText(context, "Cannot connect to server.", Toast.LENGTH_LONG).show()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Toast.makeText(context, "Please sign in.", Toast.LENGTH_LONG).show()
                //                loginService.logout();
            } else if (e.code() >= 500) {
                Toast.makeText(context, "Something bad happened.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Throwable) {
            Timber.e(e, "handleHttpError: %s", e.message)
            Toast.makeText(context, "Something bad happened.", Toast.LENGTH_LONG).show()
        }

    }
}
