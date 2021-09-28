package testdrive

import dekilla.core.container.ServerContainer
import dekilla.core.server.ServerManager

class ServerTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ServerManager.port = 9090

            val serverManager:ServerManager = ServerContainer.serverManager()
            serverManager.bind()
            serverManager.accept()
            serverManager.processing()

        }
    }
}