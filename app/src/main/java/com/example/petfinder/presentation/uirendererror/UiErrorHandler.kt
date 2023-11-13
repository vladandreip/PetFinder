package com.example.petfinder.presentation.uirendererror

import android.content.Context

interface UiErrorHandler {

    fun renderError(error: Throwable, context: Context)
}