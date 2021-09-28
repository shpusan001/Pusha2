package dekilla.core.util.socket

import java.io.Serializable
import java.net.Socket

class WrappedSocket {

    val id: String
    val socket: Socket

    constructor(socket: Socket, id: String) {
        this.socket = socket
        this.id = id
    }
}