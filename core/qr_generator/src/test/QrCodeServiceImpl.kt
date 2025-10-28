import com.andreypmi.qr_generator.QrCodeServiceImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import kotlin.test.Test

class QrCodeServiceImplTest {


    private lateinit var repository: QrCodeServiceImpl

    @Before
    fun setUp() {
        repository = QrCodeServiceImpl()
    }

    @Test
    fun `should generate QR code data for non-empty string`() {
        val data = "Hello World"

        val result = repository.generateQrCode(data)

        assertTrue(result.width > 0)
        assertTrue(result.height > 0)
        assertEquals(result.width * result.height, result.pixels.size)
    }

    @Test
    fun `should generate QR code data for empty string`() {
        val data = ""

        val result = repository.generateQrCode(data)

        assertTrue(result.width > 0)
        assertTrue(result.height > 0)
        assertEquals(result.width * result.height, result.pixels.size)
    }

    @Test
    fun `should generate QR code data for special characters`() {
        val data = "Test@123#\$%"

        val result = repository.generateQrCode(data)

        assertTrue(result.width > 0)
        assertTrue(result.height > 0)
        assertEquals(result.width * result.height, result.pixels.size)
    }

    @Test
    fun `should generate QR code with correct structure`() {
        val data = "test"

        val result = repository.generateQrCode(data)

        val hasBlackPixels = result.pixels.any { it }
        assertTrue("QR code should have some black pixels", hasBlackPixels)

        val hasWhitePixels = result.pixels.any { !it }
        assertTrue("QR code should have some white pixels", hasWhitePixels)
    }

    @Test
    fun `should generate different QR codes for different inputs`() {
        val data1 = "Hello"
        val data2 = "World"

        val result1 = repository.generateQrCode(data1)
        val result2 = repository.generateQrCode(data2)

        assertEquals(result1.width, result2.width)
        assertEquals(result1.height, result2.height)

        assertFalse("Different inputs should produce different QR codes",
            result1.pixels.contentEquals(result2.pixels))
    }

    @Test
    fun `should generate consistent QR code for same input`() {
        val data = "Consistent Data"

        val result1 = repository.generateQrCode(data)
        val result2 = repository.generateQrCode(data)

        assertEquals(result1.width, result2.width)
        assertEquals(result1.height, result2.height)
        assertTrue("Same input should produce same QR code",
            result1.pixels.contentEquals(result2.pixels))
    }

    @Test
    fun `should handle long text input`() {
        val longData = "A".repeat(100)

        val result = repository.generateQrCode(longData)

        assertTrue(result.width > 0)
        assertTrue(result.height > 0)
        assertEquals(result.width * result.height, result.pixels.size)
    }

    @Test
    fun `should generate QR code with standard size`() {
        val data = "test"

        val result = repository.generateQrCode(data)

        val expectedSize = 21 * 10
        assertEquals(expectedSize, result.width)
        assertEquals(expectedSize, result.height)
    }
}