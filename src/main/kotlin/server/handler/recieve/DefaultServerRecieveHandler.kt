package dekilla.core.server.handler.recieve

import dekilla.core.domain.SockDto
import dekilla.core.server.handler.recieve.excutor.*


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