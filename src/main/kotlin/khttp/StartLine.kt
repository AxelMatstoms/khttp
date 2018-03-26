package khttp

sealed class StartLine {
    abstract override fun toString(): String
}

class RequestLine(val method: Method, val requestUri: Uri, val httpVersion: String): StartLine() {
    override fun toString(): String {
        return "${method.name} ${requestUri.toString()} $httpVersion"
    }

}

class StatusLine(val httpVersion: String, val status: Status): StartLine() {
    override fun toString(): String {
        return "${httpVersion} ${status.statusCode} ${status.reasonPhrase}"
    }

}
