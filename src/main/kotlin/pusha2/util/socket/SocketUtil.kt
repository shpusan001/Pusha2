package pusha2.util.socket

import java.io.*
import java.net.Socket

class SocketUtil {

    private val DEFAULT_BUFFER_SIZE: Int = 10000

    /**
     * @param socket
     * @param obj
     * @throws IOException
     */
    fun send(socket: Socket, obj: Any) {
        val sockOutputStream = socket.getOutputStream()
        val sockObjectOutputStream = ObjectOutputStream(sockOutputStream)
        try {
            sockObjectOutputStream.writeObject(obj)
        } catch (e: IOException) {
            throw e
        }
    }

    /**
     * @param socket
     * @throws IOException
     * @return Any
     */
    fun recieve(socket: Socket): Any {
        val sockInputStream = socket.getInputStream()
        val sockObjectInputStream = ObjectInputStream(sockInputStream)
        try {
            val obj = sockObjectInputStream.readObject()
            return obj
        } catch (e: IOException) {
            throw e
        }
    }
}