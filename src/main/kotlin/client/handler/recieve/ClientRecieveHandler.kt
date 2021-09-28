package dekilla.core.client.handler.recieve

import dekilla.core.client.handler.recieve.excutor.ClientRecieveExcutor
import dekilla.core.domain.SockDto

interface ClientRecieveHandler {
    fun addCommand(command: String, excutor: ClientRecieveExcutor)
    fun process(sockDto: SockDto)
}