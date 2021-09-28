package dekilla.core.client.handler.exception

import dekilla.core.util.socket.WrappedSocket

interface ClientSocketExceptionHandler {

    fun connectionFaild()
    fun connectionLost(wrappedSocket: WrappedSocket)
    fun ipInputNotNumber()
}