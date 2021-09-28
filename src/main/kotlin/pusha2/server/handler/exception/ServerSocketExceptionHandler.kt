package pusha2.server.handler.exception;

import pusha2.util.socket.WrappedSocket

interface ServerSocketExceptionHandler {

    fun connectionLost(wrappedSocket: WrappedSocket)
}
