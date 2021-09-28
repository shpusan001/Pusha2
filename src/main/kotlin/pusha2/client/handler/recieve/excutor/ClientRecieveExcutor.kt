package pusha2.client.handler.recieve.excutor

import pusha2.domain.SockDto

interface ClientRecieveExcutor {
    fun excute(sockDto: SockDto)
}