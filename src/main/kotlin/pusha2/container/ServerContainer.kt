package pusha2.container

import pusha2.server.ServerManager
import pusha2.server.handler.exception.DefaultServerSocketExceptionHandler
import pusha2.server.handler.exception.ServerSocketExceptionHandler
import pusha2.server.handler.recieve.DefaultServerRecieveHandler
import pusha2.server.handler.recieve.ServerRecieveHandler
import pusha2.server.repository.HashmapSockRepository
import pusha2.server.repository.SockRepository

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