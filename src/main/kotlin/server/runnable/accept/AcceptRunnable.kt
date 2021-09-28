package dekilla.core.server.runnable.accept

import java.net.ServerSocket

interface AcceptRunnable : Runnable {
    fun setServerSocket(serverSocket: ServerSocket)
}