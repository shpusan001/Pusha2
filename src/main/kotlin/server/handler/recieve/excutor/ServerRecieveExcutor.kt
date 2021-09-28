package dekilla.core.server.handler.recieve.excutor

import dekilla.core.domain.SockDto

interface ServerRecieveExcutor {
    fun excute(sockDto: SockDto)
}