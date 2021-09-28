package dekilla.core.server.handler.exception

import dekilla.core.container.ServerContainer
import dekilla.core.container.UtilConatiner
import dekilla.core.domain.SockDto
import dekilla.core.server.repository.SockRepository
import dekilla.core.util.Log.PushaLog
import dekilla.core.util.socket.SocketUtil
import dekilla.core.util.socket.WrappedSocket

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
