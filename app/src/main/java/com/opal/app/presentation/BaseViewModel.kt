package com.opal.app.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.opal.app.R
import com.opal.app.data.cleanMessage
import com.opal.app.data.toDomain
import com.opal.app.domain.model.DomainNetworkException
import com.opal.app.domain.model.DomainUnknownException
import com.opal.app.presentation.error.ErrorPopupViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue
import kotlin.math.min

open class BaseViewModel :
    ViewModel(),
    KoinComponent {
    val errorPopupViewModel = ErrorPopupViewModel()
    protected val context: Context by inject()

    fun handleException(
        exception: Throwable,
        showPopup: Boolean = true,
    ) {
        // convert to domain exception if it's not already
        @Suppress("NAME_SHADOWING")
        val exception = exception.toDomain()
        when (exception) {
            is DomainNetworkException -> {
                when (exception) {
                    is DomainNetworkException.Cancellation -> { /* nothing to do app cancelled operation */ }
                    is DomainNetworkException.InvalidUser,
                    is DomainNetworkException.Unauthenticated,
                    -> { /* logout */ }
                    is DomainNetworkException.OutOfRange,
                    is DomainNetworkException.Aborted,
                    is DomainNetworkException.NotAvailable,
                    is DomainNetworkException.DeadlineExceeded,
                    is DomainNetworkException.NetworkError,
                    -> {
                        handleKnownException(
                            exception = exception,
                            message =
                                context.getString(R.string.error_api_network) + " " +
                                    context.getString(
                                        R.string.error_code_label,
                                        exception.code.toString(),
                                    ),
                            showPopup = showPopup,
                        )
                    }
                    is DomainNetworkException.PermissionDenied,
                    is DomainNetworkException.SerializationException,
                    is DomainNetworkException.AlreadyExists,
                    is DomainNetworkException.DataLoss,
                    is DomainNetworkException.DocumentNotFound,
                    is DomainNetworkException.FailedPrecondition,
                    is DomainNetworkException.InvalidArgument,
                    is DomainNetworkException.Ok,
                    is DomainNetworkException.ResourceExhausted,
                    is DomainNetworkException.TooManyRequest,
                    -> {
                        handleKnownException(
                            exception = exception,
                            message =
                                context.getString(R.string.error_api_generic) + " " +
                                    context.getString(
                                        R.string.error_code_label,
                                        exception.code.toString(),
                                    ),
                            showPopup = showPopup,
                        )
                    }
                    is DomainNetworkException.UnImplemented,
                    is DomainNetworkException.Internal,
                    is DomainNetworkException.Unknown,
                    -> {
                        handleUnknownException(
                            exception = exception,
                            showPopup = showPopup,
                        )
                    }
                }
            }

            is DomainUnknownException -> {
                handleUnknownException(
                    exception = exception,
                    showPopup = showPopup,
                )
            }
        }
    }

    private fun handleKnownException(
        exception: Throwable,
        message: String,
        showPopup: Boolean,
    ) {
        // log this known exception as a bread crumb
        Log.i(TAG, "knownException: $exception - ${exception.stackTrace}")
        if (showPopup) {
            errorPopupViewModel.showErrorMessage(message)
        }
    }

    private fun handleUnknownException(
        exception: Throwable,
        showPopup: Boolean,
    ) {
        // TODO log to Sentry or similar framework
        // alertRepository.captureException(exception)
        if (showPopup) {
            errorPopupViewModel.showErrorMessage(
                message = context.getString(R.string.error_api_generic),
            )
        }
    }

    protected fun <T> Flow<T>.handleException(): Flow<T> =
        retryWhen { exception, attempt ->
            // convert to domain exception if it's not already
            @Suppress("NAME_SHADOWING")
            val exception = exception.toDomain()
            Log.e(
                TAG,
                "flow exception: ${exception.cleanMessage}",
                exception,
            )
            when (exception) {
                is DomainNetworkException -> {
                    when (exception) {
                        is DomainNetworkException.Cancellation -> {
                            // do not retry, app cancelled operation
                            false
                        }
                        is DomainNetworkException.PermissionDenied -> {
                            // do not retry, this is a logout
                            false
                        }
                        is DomainNetworkException.InvalidUser,
                        is DomainNetworkException.Unauthenticated,
                        -> {
                            // do not retry, this is a logout
                            false
                        }
                        else -> {
                            // transient error, retry
                            handleRetry(attempt, exception)
                        }
                    }
                }
                else -> {
                    handleRetry(attempt, exception)
                }
            }
        }.catch {
            // do nothing
        }

    suspend fun handleRetry(
        attempt: Long,
        exception: Throwable,
    ): Boolean {
        val delayTime = min(attempt * 1000, MAX_DELAY)
        // Show popup if delay reaches threshold
        if (delayTime >= POPUP_DELAY_THRESHOLD) {
            handleException(exception)
            return false
        }
        delay(delayTime)
        return true
    }

    companion object {
        const val TAG = "BaseViewModel"
        const val MAX_DELAY = 5000L
        const val POPUP_DELAY_THRESHOLD = 15000L
    }
}
