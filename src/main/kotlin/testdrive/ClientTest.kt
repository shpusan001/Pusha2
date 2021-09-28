package testdrive

import dekilla.core.client.ClientManager
import dekilla.core.container.ClientContainer
import dekilla.core.domain.SockDto

class ClientTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ClientManager.ip="127.0.0.1"
            ClientManager.port=9090;

            val clientManager:ClientManager = ClientContainer.clientManager()
            clientManager.connect("clientme")
            clientManager.processing()

            clientManager.sendData(SockDto(clientManager.id,"NOTICE", "#", "hi", null))


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