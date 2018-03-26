import khttp.Server

fun main(args: Array<String>) {
    val server = Server(6969)
    server.listen()
}