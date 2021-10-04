package pusha2.testdrive

import pusha2.client.ClientManager
import pusha2.container.ClientContainer
import pusha2.container.ServerContainer
import pusha2.domain.SockDto
import pusha2.server.handler.recieve.ServerRecieveHandler

class ClientTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ClientManager.ip="127.0.0.1"
            ClientManager.port=9090;

            val clientManager: ClientManager = ClientContainer.clientManager()
            clientManager.connect("clientme")
            clientManager.processing()


            /*
            val clientManagerlist:ArrayList<ClientManager> = ArrayList()

            for(i in 0..100000){
                val clientManagerClone:ClientManager = ClientManager()
                clientManagerlist.add(clientManagerClone)
            }

            var i:Int = 0
            for (clientManagerClone in clientManagerlist) {
                clientManagerClone.connect(i.toString())
                clientManagerClone.sendData(SockDto(clientManager.id,"NOTICE", "#", "test[${i}]", null))
                i++
            }
             */
        }
    }
}