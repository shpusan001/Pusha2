package pusha2.client.runnable

import pusha2.client.handler.exception.ClientSocketExceptionHandler
import pusha2.client.handler.recieve.ClientRecieveHandler
import pusha2.container.ClientContainer
import pusha2.container.UtilConatiner
import pusha2.domain.SockDto
import pusha2.util.socket.SocketUtil
import pusha2.util.socket.WrappedSocket
import java.net.Socket

class DefaultClientRecieveRunnable : ClientRecieveRunnable {

    private val wrappedSocket: WrappedSocket

    private val socketUtil: SocketUtil
    private val clientRecieveHandler: ClientRecieveHandler
    private val clientSocketExceptionHandler: ClientSocketExceptionHandler

    constructor(wrappedSocket: WrappedSocket) {
        this.wrappedSocket = wrappedSocket

        socketUtil = UtilConatiner.socketUtil()
        clientRecieveHandler = ClientContainer.clientRecieveHandler()
        clientSocketExceptionHandler = ClientContainer.clientSocketExceptionHandler()
    }

    override fun run() {
        val socket: Socket = wrappedSocket.socket
        while (!Thread.interrupted()) {
            try {
                val sockDto: SockDto = socketUtil.recieve(socket) as SockDto
                clientRecieveHandler.process(sockDto)
            } catch (e: Exception) {
                clientSocketExceptionHandler.connectionLost(wrappedSocket)
                Thread.currentThread().interrupt()
            }
        }
    }
}