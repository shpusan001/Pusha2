package dekilla.core.container

import dekilla.core.client.ClientManager
import dekilla.core.client.handler.exception.ClientSocketExceptionHandler
import dekilla.core.client.handler.exception.DefaultClientSocketExceptionHandler
import dekilla.core.client.handler.recieve.ClientRecieveHandler
import dekilla.core.client.handler.recieve.DefaultClientRecieveHandler

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