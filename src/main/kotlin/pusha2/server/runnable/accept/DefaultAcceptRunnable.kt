package pusha2.server.runnable.accept

import pusha2.container.ServerContainer
import pusha2.container.UtilConatiner
import pusha2.domain.SockDto
import pusha2.server.repository.SockRepository
import pusha2.util.Log.PushaLog
import pusha2.util.socket.SocketUtil
import pusha2.util.socket.WrappedSocket
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

class DefaultAcceptRunnable : AcceptRunnable {

    private lateinit var serverSocket: ServerSocket
    private lateinit var sockRepository: SockRepository
    private lateinit var socketUtil: SocketUtil

    override fun setServerSocket(serverSocket: ServerSocket) {
        this.serverSocket = serverSocket
        this.sockRepository = ServerContainer.sockRepository()
        this.socketUtil = UtilConatiner.socketUtil()
    }


    override fun run() {
        while (!Thread.interrupted()) {
            val socket: Socket = serverSocket.accept()
            Thread(Acception(socket)).start()
        }
    }

    inner class Acception : Runnable{

        val socket:Socket

        constructor(socket:Socket){
            this.socket = socket
        }

        override fun run() {
            if (!Thread.interrupted()) {
                var connectRequest: SockDto

                do {
                    try {
                        connectRequest = socketUtil.recieve(socket) as SockDto
                    }catch (e:SocketException){
                        PushaLog.log("abnormal connection")
                        return
                    }
                } while (connectRequest.command != "CONNECT")

                val sockId: String = connectRequest.data
                val wrappedSocket = WrappedSocket(socket, sockId)
                sockRepository.add(sockId, wrappedSocket)

                PushaLog.log("[${sockId}] is connected")
            }
        }

    }
}