package com.andreypmi.qr_generator

import com.andreypmi.core_domain.service.QrCodeService
import qrcode.QRCode
import qrcode.color.Colors
import qrcode.raw.ErrorCorrectionLevel
import kotlin.io.encoding.Base64


class QrCodeServiceImpl : QrCodeService {
    override fun generateQrCode(data: String): ByteArray {
        return QRCode.ofSquares()
            .withGradientColor(
                Colors.rgba(255, 0, 0, 0),
                Colors.rgba(138, 43, 226, 180)
            )
            .withBackgroundColor(Colors.TRANSPARENT)
            .withSize(12)
            .build(data)
            .renderToBytes()
    }
}