package pusha2.server.handler.recieve

import pusha2.domain.SockDto
import pusha2.server.handler.recieve.excutor.NoticeExcutor
import pusha2.server.handler.recieve.excutor.ServerRecieveExcutor


class DefaultServerRecieveHandler : ServerRecieveHandler {

    private val commandRepository: HashMap<String, ServerRecieveExcutor> = HashMap()

    constructor() {
        commandRepository.put("NOTICE", NoticeExcutor())
    }

    override fun addCommand(command: String, excutor: ServerRecieveExcutor) {
        commandRepository.put(command, excutor)
    }

    override fun process(sockDto: SockDto) {
        try {
            if (commandRepository.containsKey(sockDto.command)) {
                commandRepository.get(sockDto.command)!!.excute(sockDto)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}