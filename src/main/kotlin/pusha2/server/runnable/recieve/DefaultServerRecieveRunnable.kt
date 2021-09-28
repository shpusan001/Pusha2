package pusha2.server.runnable.recieve

import pusha2.container.ServerContainer
import pusha2.container.UtilConatiner
import pusha2.domain.SockDto
import pusha2.server.repository.SockRepository
import pusha2.server.handler.exception.ServerSocketExceptionHandler
import pusha2.server.handler.recieve.ServerRecieveHandler
import pusha2.util.socket.SocketUtil
import pusha2.util.socket.WrappedSocket
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