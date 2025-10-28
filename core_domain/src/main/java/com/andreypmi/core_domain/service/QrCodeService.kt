package com.andreypmi.core_domain.service

import com.andreypmi.core_domain.models.QrCodeData

interface QrCodeService {
    fun generateQrCode(data: String): QrCodeData
}