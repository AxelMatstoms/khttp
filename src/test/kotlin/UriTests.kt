import khttp.uriEncode
import org.junit.Test

class UriTests {

    @Test
    fun testUriEncode() {
        assert("dank memes".uriEncode() == "dank%20memes")
    }
}