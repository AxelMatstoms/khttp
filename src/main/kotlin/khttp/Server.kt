package khttp

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class Server(val port: Int) {
    fun listen() {
        val serverSocket = ServerSocket(port)
        while (true) {
            val request = serverSocket.accept()
            handleRequest(request)
        }
    }

    fun handleRequest(request: Socket) {
        val inputReader = BufferedReader(InputStreamReader(request.getInputStream()))
        for (line in inputReader.lines()) {
            println(line)
        }
        request.close()
    }
}