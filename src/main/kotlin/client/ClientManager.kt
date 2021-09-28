package dekilla.core.client

import dekilla.core.client.handler.exception.ClientSocketExceptionHandler
import dekilla.core.client.runnable.ClientRecieveRunnable
import dekilla.core.client.runnable.DefaultClientRecieveRunnable
import dekilla.core.container.ClientContainer
import dekilla.core.container.UtilConatiner
import dekilla.core.domain.SockDto
import dekilla.core.util.socket.*
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

            //id send to server
            val connectRequest:SockDto = SockDto(id ,"CONNECT", "#", id, null)
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