package dekilla.core.server.handler.recieve.excutor

import dekilla.core.domain.SockDto
import dekilla.core.util.Log.PushaLog

class NoticeExcutor : ServerRecieveExcutor {
    override fun excute(sockDto: SockDto) {
        PushaLog.log("[${sockDto.from}]==> " + sockDto.data)
    }

}