package com.andreypmi.core_domain.service


interface QrCodeService {
    fun generateQrCode(data: String): ByteArray
}