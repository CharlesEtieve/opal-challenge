package com.opal.app.domain.model

sealed class DomainException(
    message: String? = null,
) : Exception(message)

sealed class DomainNetworkException(
    message: String? = null,
    val code: Int,
) : DomainException(
        message,
    ) {
    class Cancellation(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )

    class SerializationException(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )

    class NetworkError(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class TooManyRequest(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class NotAvailable(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class InvalidUser(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class Ok(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class Unknown(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class InvalidArgument(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )

    class DeadlineExceeded(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )

    class DocumentNotFound(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )

    class AlreadyExists(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class PermissionDenied(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )

    class ResourceExhausted(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )

    class FailedPrecondition(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )

    class Aborted(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class OutOfRange(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class UnImplemented(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class Internal(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class DataLoss(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(message, code)

    class Unauthenticated(
        message: String? = null,
        code: Int,
    ) : DomainNetworkException(
            message,
            code,
        )
}

class DomainUnknownException(
    message: String? = null,
) : DomainException(message)
