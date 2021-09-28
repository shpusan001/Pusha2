package pusha2.client.handler.recieve

import pusha2.client.handler.recieve.excutor.ClientRecieveExcutor
import pusha2.domain.SockDto

interface ClientRecieveHandler {
    fun addCommand(command: String, excutor: ClientRecieveExcutor)
    fun process(sockDto: SockDto)
}