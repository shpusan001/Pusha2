package pusha2.server.handler.recieve

import pusha2.domain.SockDto
import pusha2.server.handler.recieve.excutor.ServerRecieveExcutor

interface ServerRecieveHandler {
    fun addCommand(command: String, excutor: ServerRecieveExcutor)
    fun process(sockDto: SockDto)
}