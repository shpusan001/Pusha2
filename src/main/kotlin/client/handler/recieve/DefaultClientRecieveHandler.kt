package dekilla.core.client.handler.recieve

import dekilla.core.client.handler.recieve.excutor.*
import dekilla.core.domain.SockDto

class DefaultClientRecieveHandler : ClientRecieveHandler {
    private val commandRepository: HashMap<String, ClientRecieveExcutor> = HashMap()

    constructor() {
        commandRepository.put("NOTICE", NoticeExcutor())
    }

    override fun addCommand(command: String, excutor: ClientRecieveExcutor) {
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