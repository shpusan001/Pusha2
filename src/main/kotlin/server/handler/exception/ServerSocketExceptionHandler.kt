package dekilla.core.server.handler.exception;

import dekilla.core.util.socket.WrappedSocket

interface ServerSocketExceptionHandler {

    fun connectionLost(wrappedSocket: WrappedSocket)
}
