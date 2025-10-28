package com.opal.app.data

import com.opal.app.domain.model.DomainException
import com.opal.app.domain.model.DomainNetworkException
import com.opal.app.domain.model.DomainUnknownException
import kotlin.coroutines.cancellation.CancellationException

fun Throwable.toDomain(extraCode: Int = 0): DomainException =
    when (this) {
        is DomainException -> this
        // is StatusException -> toDomain(extraCode)
        is kotlinx.serialization.SerializationException ->
            DomainNetworkException.SerializationException(
                cleanMessage,
                SERIALIZATION_EXCEPTION_CODE,
            )
        is CancellationException ->
            DomainNetworkException.Cancellation(
                cleanMessage,
                CANCELLATION_EXCEPTION_CODE,
            )
        else -> DomainUnknownException(cleanMessage)
    }.also {
        // set the stacktrace from the original exception
        it.stackTrace = stackTrace
    }

// fun StatusException.toDomain(extraCode: Int): DomainException {
//    return when (status.code) {
//        Status.Code.OK -> DomainNetworkException.Ok(
//            cleanMessage,
//            extraCode + Status.Code.OK.value()
//        )
//        Status.Code.CANCELLED -> DomainNetworkException.Cancellation(
//            cleanMessage,
//            extraCode + Status.Code.CANCELLED.value()
//        )
//        Status.Code.UNKNOWN -> DomainNetworkException.Unknown(
//            cleanMessage,
//            extraCode + Status.Code.UNKNOWN.value()
//        )
//        Status.Code.INVALID_ARGUMENT -> invalidArgumentToDomain(extraCode)
//        Status.Code.DEADLINE_EXCEEDED -> DomainNetworkException.DeadlineExceeded(
//            cleanMessage,
//            extraCode + Status.Code.DEADLINE_EXCEEDED.value()
//        )
//        Status.Code.NOT_FOUND -> DomainNetworkException.DocumentNotFound(
//            cleanMessage,
//            extraCode + Status.Code.NOT_FOUND.value()
//        )
//        Status.Code.ALREADY_EXISTS -> DomainNetworkException.AlreadyExists(
//            cleanMessage,
//            extraCode + Status.Code.ALREADY_EXISTS.value()
//        )
//        Status.Code.PERMISSION_DENIED -> DomainNetworkException.PermissionDenied(
//            cleanMessage,
//            extraCode + Status.Code.PERMISSION_DENIED.value()
//        )
//        Status.Code.RESOURCE_EXHAUSTED -> DomainNetworkException.ResourceExhausted(
//            cleanMessage,
//            extraCode + Status.Code.RESOURCE_EXHAUSTED.value()
//        )
//        Status.Code.FAILED_PRECONDITION -> DomainNetworkException.FailedPrecondition(
//            cleanMessage,
//            extraCode + Status.Code.FAILED_PRECONDITION.value()
//        )
//        Status.Code.ABORTED -> DomainNetworkException.Aborted(
//            cleanMessage,
//            extraCode + Status.Code.ABORTED.value()
//        )
//        Status.Code.OUT_OF_RANGE -> DomainNetworkException.OutOfRange(
//            cleanMessage,
//            extraCode + Status.Code.OUT_OF_RANGE.value()
//        )
//        Status.Code.UNIMPLEMENTED -> DomainNetworkException.UnImplemented(
//            cleanMessage,
//            extraCode + Status.Code.UNIMPLEMENTED.value()
//        )
//        Status.Code.INTERNAL -> DomainNetworkException.Internal(
//            cleanMessage,
//            extraCode + Status.Code.INTERNAL.value()
//        )
//        Status.Code.UNAVAILABLE -> DomainNetworkException.NotAvailable(
//            cleanMessage,
//            extraCode + Status.Code.UNAVAILABLE.value()
//        )
//        Status.Code.DATA_LOSS -> DomainNetworkException.DataLoss(
//            cleanMessage,
//            extraCode + Status.Code.DATA_LOSS.value()
//        )
//        Status.Code.UNAUTHENTICATED -> DomainNetworkException.Unauthenticated(
//            cleanMessage,
//            extraCode + Status.Code.UNAUTHENTICATED.value()
//        )
//        null -> DomainUnknownException(cleanMessage)
//    }
// }

val Throwable.cleanMessage: String?
    get() = message ?: cause?.message

const val CANCELLATION_EXCEPTION_CODE = 1001
const val SERIALIZATION_EXCEPTION_CODE = 1002
