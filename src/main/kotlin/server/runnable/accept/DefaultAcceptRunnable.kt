package dekilla.core.server.runnable.accept

import dekilla.core.container.ServerContainer
import dekilla.core.container.UtilConatiner
import dekilla.core.domain.SockDto
import dekilla.core.server.repository.SockRepository
import dekilla.core.util.Log.PushaLog
import dekilla.core.util.socket.SocketUtil
import dekilla.core.util.socket.WrappedSocket
import java.net.ServerSocket
import java.net.Socket

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
                var connectRequest:SockDto

                do {
                    connectRequest = socketUtil.recieve(socket) as SockDto
                } while (connectRequest.command != "CONNECT")

                val sockId: String = connectRequest.data
                val wrappedSocket = WrappedSocket(socket, sockId)
                sockRepository.add(sockId, wrappedSocket)

                PushaLog.log("[${sockId}] is connected")
            }
        }

    }
}