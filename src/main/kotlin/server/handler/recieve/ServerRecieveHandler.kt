package dekilla.core.server.handler.recieve

import dekilla.core.domain.SockDto
import dekilla.core.server.handler.recieve.excutor.ServerRecieveExcutor

interface ServerRecieveHandler {
    fun addCommand(command: String, excutor: ServerRecieveExcutor)
    fun process(sockDto: SockDto)
}