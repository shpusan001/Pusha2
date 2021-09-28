package dekilla.core.container

import dekilla.core.server.ServerManager
import dekilla.core.server.handler.exception.DefaultServerSocketExceptionHandler
import dekilla.core.server.handler.exception.ServerSocketExceptionHandler
import dekilla.core.server.handler.recieve.DefaultServerRecieveHandler
import dekilla.core.server.handler.recieve.ServerRecieveHandler
import dekilla.core.server.repository.HashmapSockRepository
import dekilla.core.server.repository.SockRepository

class ServerContainer {

    companion object {

        private var serverManager: ServerManager? = null
        private var serverSocketExceptionHandler: ServerSocketExceptionHandler? = null
        private var serverRecieveHandler: ServerRecieveHandler? = null
        private var sockRepository: SockRepository? = null

        fun serverManager(): ServerManager {
            if (serverManager == null)
                serverManager = ServerManager()
            return serverManager!!
        }

        fun serverSocketExceptionHandler(): ServerSocketExceptionHandler {
            if (serverSocketExceptionHandler == null)
                serverSocketExceptionHandler = DefaultServerSocketExceptionHandler()
            return serverSocketExceptionHandler!!
        }

        fun serverRecieveHandler(): ServerRecieveHandler {
            if (serverRecieveHandler == null)
                serverRecieveHandler = DefaultServerRecieveHandler()
            return serverRecieveHandler!!
        }

        fun sockRepository(): SockRepository {
            if (sockRepository == null)
                sockRepository = HashmapSockRepository()
            return sockRepository!!
        }
    }
}