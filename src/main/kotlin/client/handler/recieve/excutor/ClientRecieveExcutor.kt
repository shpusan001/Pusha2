package dekilla.core.client.handler.recieve.excutor

import dekilla.core.domain.SockDto

interface ClientRecieveExcutor {
    fun excute(sockDto: SockDto)
}