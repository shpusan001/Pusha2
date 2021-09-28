package pusha2.server

import pusha2.container.ServerContainer
import pusha2.container.UtilConatiner
import pusha2.domain.SockDto
import pusha2.server.repository.SockRepository
import pusha2.server.runnable.accept.AcceptRunnable
import pusha2.server.runnable.accept.DefaultAcceptRunnable
import pusha2.server.runnable.recieve.DefaultServerRecieveRunnable
import pusha2.server.runnable.recieve.ServerRecieveRunnable
import pusha2.util.Log.PushaLog
import pusha2.util.socket.SocketUtil
import java.net.ServerSocket
import java.net.Socket

class ServerManager {

    companion object {
        var port: Int = 33333
    }

    lateinit var serverSocket: ServerSocket
    private lateinit var acceptThread: Thread
    private lateinit var acceptRunnable: AcceptRunnable
    private lateinit var recieveThread: Thread
    private lateinit var recieveRunnable: ServerRecieveRunnable

    private val sockRepository: SockRepository
    private val socketUtil: SocketUtil


    constructor() {
        this.sockRepository = ServerContainer.sockRepository()
        this.socketUtil = UtilConatiner.socketUtil()
    }

    fun bind() {
        serverSocket = ServerSocket(port)

        PushaLog.log("Bind " + port)
    }


    fun accept() {
        acceptRunnable = DefaultAcceptRunnable()
        acceptRunnable.setServerSocket(serverSocket)
        acceptThread = Thread(acceptRunnable)
        acceptThread.start()

        PushaLog.log("Accepting start")
    }

    fun processing() {
        recieveRunnable = DefaultServerRecieveRunnable()
        recieveThread = Thread(recieveRunnable)
        recieveThread.start()

        PushaLog.log("Processing start")
    }

    fun sendData(id:String, data:SockDto){
        if(sockRepository.isContain(id)){
            val socket:Socket = sockRepository.get(id)!!.socket
            socketUtil.send(socket, data)
        }
    }

    fun close() {
        acceptThread.interrupt()
        recieveThread.interrupt()
        sockRepository.close()
    }
}