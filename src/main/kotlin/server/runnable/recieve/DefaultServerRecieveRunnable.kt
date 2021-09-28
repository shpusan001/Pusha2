package dekilla.core.server.runnable.recieve

import dekilla.core.container.ServerContainer
import dekilla.core.container.UtilConatiner
import dekilla.core.domain.SockDto
import dekilla.core.server.repository.SockRepository
import dekilla.core.server.handler.exception.ServerSocketExceptionHandler
import dekilla.core.server.handler.recieve.ServerRecieveHandler
import dekilla.core.util.socket.SocketUtil
import dekilla.core.util.socket.WrappedSocket
import java.util.concurrent.CopyOnWriteArrayList

class DefaultServerRecieveRunnable : ServerRecieveRunnable {

    private val sockRepository: SockRepository
    private val sockList: CopyOnWriteArrayList<WrappedSocket>

    private val recieveThreadMap: HashMap<WrappedSocket, Thread> = HashMap()

    private val socketUtil: SocketUtil
    private val serverRecieveHandler: ServerRecieveHandler
    private val serverSocketExceptionHandler: ServerSocketExceptionHandler

    constructor() {
        sockRepository = ServerContainer.sockRepository()
        socketUtil = UtilConatiner.socketUtil()
        serverRecieveHandler = ServerContainer.serverRecieveHandler()
        serverSocketExceptionHandler = ServerContainer.serverSocketExceptionHandler()

        this.sockList = sockRepository.getList()
    }

    override fun run() {
        while (!Thread.interrupted()) {
            for (wrappedSocket in sockList) {
                if (!recieveThreadMap.containsKey(wrappedSocket)) {
                    val recieveThread = Thread(RecieveUnit(wrappedSocket))
                    recieveThreadMap.put(wrappedSocket, recieveThread)
                    sockList.remove(wrappedSocket)
                    recieveThread.start()
                }
            }
        }
    }

    inner class RecieveUnit : Runnable {

        private val wrappedSocket: WrappedSocket

        constructor(wrappedSocket: WrappedSocket) {
            this.wrappedSocket = wrappedSocket
        }

        override fun run() {
            try {
                val sockDto: SockDto = socketUtil.recieve(wrappedSocket.socket) as SockDto
                serverRecieveHandler.process(sockDto)
                recieveThreadMap.remove(wrappedSocket)
                if (sockRepository.isContain(wrappedSocket)) {
                    sockList.add(wrappedSocket)
                }
            } catch (e: Exception) {
                serverSocketExceptionHandler.connectionLost(wrappedSocket)
            }
        }
    }
}