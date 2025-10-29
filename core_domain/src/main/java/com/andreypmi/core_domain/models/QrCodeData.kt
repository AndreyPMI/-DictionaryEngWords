package com.andreypmi.core_domain.models

data class QrCodeData(
    val pixels: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QrCodeData

        if (!pixels.contentEquals(other.pixels)) return false

        return true
    }

    override fun hashCode(): Int {
        return pixels.contentHashCode()
    }
}