package pusha2.server.handler.recieve.excutor

import pusha2.domain.SockDto
import pusha2.util.Log.PushaLog

class NoticeExcutor : ServerRecieveExcutor {
    override fun excute(sockDto: SockDto) {
        PushaLog.log("[${sockDto.from}]==> " + sockDto.data)
    }

}