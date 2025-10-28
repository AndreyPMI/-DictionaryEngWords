package com.andreypmi.qr_generator

import com.andreypmi.core_domain.models.QrCodeData

class QrMatrix(val size: Int) {
    private val data = BooleanArray(size * size)

    operator fun get(x: Int, y: Int): Boolean {
        require(x in 0 until size && y in 0 until size) { "Coordinates out of bounds: ($x, $y)" }
        return data[y * size + x]
    }

    operator fun set(x: Int, y: Int, value: Boolean) {
        require(x in 0 until size && y in 0 until size) { "Coordinates out of bounds: ($x, $y)" }
        data[y * size + x] = value
    }

    fun addFinderPattern(centerX: Int, centerY: Int) {
        for (x in -4..4) {
            for (y in -4..4) {
                val absX = centerX + x
                val absY = centerY + y

                if (absX in 0 until size && absY in 0 until size) {
                    val isBlack = when {
                        x == -4 || x == 4 -> true
                        y == -4 || y == 4 -> true
                        x in -2..2 && y in -2..2 -> true
                        else -> false
                    }
                    set(absX, absY, isBlack)
                }
            }
        }
    }

    fun addTimingPatterns() {
        for (i in 8 until size - 8) {
            this[i, 6] = i % 2 == 0
            this[6, i] = i % 2 == 0
        }
    }

    fun isReserved(x: Int, y: Int): Boolean {
        if ((x < 9 && y < 9) ||
            (x > size - 9 && y < 9) ||
            (x < 9 && y > size - 9))
            return true

        if (x == 6 || y == 6) return true

        return false
    }

    fun toQrCodeData(scale: Int = 1): QrCodeData {
        val width = size * scale
        val height = size * scale
        val pixels = BooleanArray(width * height)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val matrixX = x / scale
                val matrixY = y / scale
                pixels[y * width + x] = this[matrixX, matrixY]
            }
        }

        return QrCodeData(width, height, pixels)
    }

    fun toDebugString(): String {
        return buildString {
            for (y in 0 until size) {
                for (x in 0 until size) {
                    append(if (this@QrMatrix[x, y]) "██" else "  ")
                }
                appendLine()
            }
        }
    }
}