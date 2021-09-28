package pusha2.client.handler.exception

import pusha2.util.socket.WrappedSocket

interface ClientSocketExceptionHandler {

    fun connectionFaild()
    fun connectionLost(wrappedSocket: WrappedSocket)
    fun ipInputNotNumber()
}