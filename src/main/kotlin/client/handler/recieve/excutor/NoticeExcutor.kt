package dekilla.core.client.handler.recieve.excutor

import dekilla.core.domain.SockDto
import dekilla.core.util.Log.PushaLog

class NoticeExcutor : ClientRecieveExcutor {
    override fun excute(sockDto: SockDto) {
        PushaLog.log("[${sockDto.from}]==> " + sockDto.data)
    }
}