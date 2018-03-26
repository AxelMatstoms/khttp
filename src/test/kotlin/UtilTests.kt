import khttp.toMap
import org.junit.Test

class UtilTests {
    @Test
    fun testStringToMap() {
        val testStr = "{\"dank\": \"memes\", \"normie\": \"trash\"}"
        testStr.toMap<String, String>()
    }
}