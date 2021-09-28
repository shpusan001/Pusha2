package dekilla.core.client.runnable

import dekilla.core.client.handler.exception.ClientSocketExceptionHandler
import dekilla.core.client.handler.recieve.ClientRecieveHandler
import dekilla.core.container.ClientContainer
import dekilla.core.container.UtilConatiner
import dekilla.core.domain.SockDto
import dekilla.core.util.socket.SocketUtil
import dekilla.core.util.socket.WrappedSocket
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