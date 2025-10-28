import com.andreypmi.qr_generator.QrMatrix
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import kotlin.test.assertFailsWith


class QrMatrixTest {

    @Test
    fun `should create matrix with correct size`() {
        val matrix = QrMatrix(21)

        assertEquals(21, matrix.size)
    }

    @Test
    fun `should get and set values correctly`() {
        val matrix = QrMatrix(5)

        matrix[2, 3] = true
        assertTrue(matrix[2, 3])

        matrix[2, 3] = false
        assertFalse(matrix[2, 3])
    }

    @Test
    fun `should throw exception for out of bounds access`() {
        val matrix = QrMatrix(5)

        assertFailsWith<IllegalArgumentException> {
            matrix[-1, 0]
        }

        assertFailsWith<IllegalArgumentException> {
            matrix[0, -1]
        }

        assertFailsWith<IllegalArgumentException> {
            matrix[5, 0]
        }

        assertFailsWith<IllegalArgumentException> {
            matrix[0, 5]
        }
    }

    @Test
    fun `should add finder pattern correctly`() {
        val matrix = QrMatrix(10)

        matrix.addFinderPattern(4, 4)

        assertTrue(matrix[0, 0])
        assertTrue(matrix[8, 0])
        assertTrue(matrix[0, 8])
        assertTrue(matrix[8, 8])

        assertTrue(matrix[4, 4])
        assertTrue(matrix[3, 3])
        assertTrue(matrix[5, 5])

        assertFalse(matrix[1, 1])
        assertFalse(matrix[7, 7])
    }

    @Test
    fun `should add finder pattern near edges without errors`() {
        val matrix = QrMatrix(5)

        matrix.addFinderPattern(0, 0)
        matrix.addFinderPattern(4, 4)
    }

    @Test
    fun `should add timing patterns correctly`() {
        val matrix = QrMatrix(21)

        matrix.addTimingPatterns()

        for (x in 8 until 13) {
            assertEquals(x % 2 == 0, matrix[x, 6])
        }
        for (y in 8 until 13) {
            assertEquals(y % 2 == 0, matrix[6, y])
        }
    }

    @Test
    fun `should correctly identify reserved areas`() {
        val matrix = QrMatrix(21)

        assertTrue(matrix.isReserved(2, 2))
        assertTrue(matrix.isReserved(18, 2))
        assertTrue(matrix.isReserved(2, 18))

        assertTrue(matrix.isReserved(6, 10))
        assertTrue(matrix.isReserved(10, 6))

        // Non-reserved areas
        assertFalse(matrix.isReserved(10, 10))
        assertFalse(matrix.isReserved(20, 20))
    }

    @Test
    fun `should convert to QrCodeData with correct dimensions`() {
        val matrix = QrMatrix(5)
        matrix[2, 2] = true

        val qrCodeData = matrix.toQrCodeData(scale = 3)

        assertEquals(15, qrCodeData.width)
        assertEquals(15, qrCodeData.height)
        assertEquals(225, qrCodeData.pixels.size)
    }

    @Test
    fun `should scale pixels correctly in QrCodeData`() {
        val matrix = QrMatrix(2)
        matrix[0, 0] = true
        matrix[1, 1] = true

        val qrCodeData = matrix.toQrCodeData(scale = 2)

        assertTrue(qrCodeData.pixels[0])
        assertTrue(qrCodeData.pixels[1])

    }

    @Test
    fun `toDebugString should return correct representation`() {
        val matrix = QrMatrix(2)
        matrix[0, 0] = true
        matrix[1, 1] = true

        val debugString = matrix.toDebugString()

        val lines = debugString.trim().lines()
        assertEquals(2, lines.size)
        assertEquals("██", lines[0].trimEnd())
        assertEquals("  ██", lines[1].trimEnd())
    }
}