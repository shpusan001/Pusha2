package pusha2.server.handler.exception

import pusha2.container.ServerContainer
import pusha2.container.UtilConatiner
import pusha2.server.repository.SockRepository
import pusha2.util.Log.PushaLog
import pusha2.util.socket.SocketUtil
import pusha2.util.socket.WrappedSocket

class DefaultServerSocketExceptionHandler : ServerSocketExceptionHandler {

    private val sockRepository: SockRepository
    val socketUtil: SocketUtil

    constructor() {
        this.sockRepository = ServerContainer.sockRepository()
        this.socketUtil = UtilConatiner.socketUtil()
    }

    override fun connectionLost(wrappedSocket: WrappedSocket) {
        val sockId = wrappedSocket.id
        sockRepository.remove(sockId)

        wrappedSocket.socket.close()
        PushaLog.log("[${sockId}] is disconnected")
    }
}
