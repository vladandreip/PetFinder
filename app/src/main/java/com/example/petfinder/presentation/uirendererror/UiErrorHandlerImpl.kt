package com.example.petfinder.presentation.uirendererror

import android.app.AlertDialog
import android.content.Context
import com.example.petfinder.R
import com.example.petfinder.domain.exception.AppException.ForbiddenException
import com.example.petfinder.domain.exception.AppException.InternalServerErrorException
import com.example.petfinder.domain.exception.AppException.NotFoundException
import com.example.petfinder.domain.exception.AppException.UnknownException
import javax.inject.Inject

class UiErrorHandlerImpl @Inject constructor() : UiErrorHandler {

    override fun renderError(error: Throwable, context: Context) {
        val message = renderMessage(error, context)
        showUiAlert(message, context)
    }

    private fun renderMessage(error: Throwable, context: Context): String {
        return when (error) {
            is NotFoundException -> context.getString(R.string.error_message)
            is InternalServerErrorException -> context.getString(R.string.error_message_fix)
            is UnknownException -> context.getString(R.string.error_message)
            is ForbiddenException -> context.getString(R.string.error_message_2)
            else -> context.getString(R.string.error_message_default)
        }
    }

    private fun showUiAlert(message: String, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder
            .setMessage(message)
            .setTitle(R.string.alert_title)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}