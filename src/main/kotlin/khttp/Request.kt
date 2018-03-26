package khttp

data class Request(val method: Method,
                   val path: String,
                   val httpVersion: String,
                   val headers: HashMap<String, String>) {
    private val crlf = "\r\n"
    fun makeHeader() {
        var header = ""

    }
}