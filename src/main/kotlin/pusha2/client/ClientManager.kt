package pusha2.client

import pusha2.client.handler.exception.ClientSocketExceptionHandler
import pusha2.client.runnable.ClientRecieveRunnable
import pusha2.client.runnable.DefaultClientRecieveRunnable
import pusha2.container.ClientContainer
import pusha2.container.UtilConatiner
import pusha2.domain.SockDto
import pusha2.util.socket.SocketUtil
import pusha2.util.socket.WrappedSocket
import java.net.Socket

class ClientManager {

    companion object {
        var ip: String = "xxx.xxx.xxx.xxx"
        var port: Int = 33333
    }

    var socket: Socket? = null

    var wrappedSocket: WrappedSocket? = null
    var connectedId: String = ""
    var id:String = "null"

    private lateinit var recieveThread: Thread
    private lateinit var recieveRunnable: ClientRecieveRunnable

    private val socketUtil: SocketUtil
    private val clientSocketExceptionHandler: ClientSocketExceptionHandler

    constructor() {
        this.socketUtil = UtilConatiner.socketUtil()
        this.clientSocketExceptionHandler = ClientContainer.clientSocketExceptionHandler()
    }

    fun connect(id:String): WrappedSocket? {
        //connect
        try {
            socket = Socket(ip, port)
            //id initialization
            wrappedSocket = WrappedSocket(socket!!, id)
            this.id = id

            //id send to pusha2.server
            val connectRequest: SockDto = SockDto(id ,"CONNECT", "#", id, null)
            socketUtil.send(socket!!, connectRequest)

            return wrappedSocket
        } catch (e: Exception) {
            clientSocketExceptionHandler.connectionFaild()
            //throw e
        }
        return null
    }

    fun processing() {
        if (wrappedSocket != null) {
            recieveRunnable = DefaultClientRecieveRunnable(wrappedSocket!!)
            recieveThread = Thread(recieveRunnable)
            recieveThread.start()
        }
    }

    fun close() {
        recieveThread.interrupt()
        if (wrappedSocket != null) {
            wrappedSocket!!.socket.close()
        }
    }

    fun sendData(sockDto: SockDto) {
        if(socket!=null) {
            socketUtil.send(socket!!, sockDto)
        }
    }
}