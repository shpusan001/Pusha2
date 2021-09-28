package pusha2.server.handler.recieve.excutor

import pusha2.domain.SockDto

interface ServerRecieveExcutor {
    fun excute(sockDto: SockDto)
}