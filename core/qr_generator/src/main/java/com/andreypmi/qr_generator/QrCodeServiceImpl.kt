package com.andreypmi.qr_generator

import com.andreypmi.core_domain.models.QrCodeData
import com.andreypmi.core_domain.service.QrCodeService

class QrCodeServiceImpl : QrCodeService {
    override fun generateQrCode(data: String): QrCodeData {
        val matrix = createQrMatrix(data)
        return matrix.toQrCodeData(scale = 10)
    }

    private fun createQrMatrix(data: String): QrMatrix {
        val matrix = QrMatrix(21)

        matrix.addFinderPattern(3, 3)
        matrix.addFinderPattern(17, 3)
        matrix.addFinderPattern(3, 17)

        matrix.addTimingPatterns()

        matrix[8, 13] = true

        addSimpleData(matrix, data)

        return matrix
    }

    private fun addSimpleData(matrix: QrMatrix, data: String) {
        val bits = data.toByteArray(Charsets.UTF_8).flatMap { byte ->
            (0..7).map { bit ->
                (byte.toInt() shr (7 - bit)) and 1 == 1
            }
        }

        var bitIndex = 0
        for (y in matrix.size - 1 downTo 0 step 2) {
            for (x in matrix.size - 1 downTo 0) {
                if (!matrix.isReserved(x, y) && bitIndex < bits.size) {
                    matrix[x, y] = bits[bitIndex++]
                }
            }
        }
    }
}