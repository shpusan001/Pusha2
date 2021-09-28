package pusha2.testdrive

import pusha2.container.ServerContainer
import pusha2.server.ServerManager

class ServerTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ServerManager.port = 9090

            val serverManager: ServerManager = ServerContainer.serverManager()
            serverManager.bind()
            serverManager.accept()
            serverManager.processing()
        }
    }
}