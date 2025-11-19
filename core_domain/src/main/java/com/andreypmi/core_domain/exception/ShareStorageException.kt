package com.andreypmi.core_domain.exception

class ShareStorageException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)