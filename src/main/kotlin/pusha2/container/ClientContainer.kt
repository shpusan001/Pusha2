package pusha2.container

import pusha2.client.ClientManager
import pusha2.client.handler.exception.ClientSocketExceptionHandler
import pusha2.client.handler.exception.DefaultClientSocketExceptionHandler
import pusha2.client.handler.recieve.ClientRecieveHandler
import pusha2.client.handler.recieve.DefaultClientRecieveHandler

class ClientContainer {

    companion object {

        private var clientManager: ClientManager? = null
        private var clientRecieveHandler: ClientRecieveHandler? = null
        private var clientSocketExceptionHandler: ClientSocketExceptionHandler? = null

        fun clientManager(): ClientManager {
            if (clientManager == null)
                clientManager = ClientManager()
            return clientManager!!
        }

        fun clientRecieveHandler(): ClientRecieveHandler {
            if (clientRecieveHandler == null)
                clientRecieveHandler = DefaultClientRecieveHandler()
            return clientRecieveHandler!!
        }

        fun clientSocketExceptionHandler(): ClientSocketExceptionHandler {
            if (clientSocketExceptionHandler == null)
                clientSocketExceptionHandler = DefaultClientSocketExceptionHandler()
            return clientSocketExceptionHandler!!
        }
    }
}