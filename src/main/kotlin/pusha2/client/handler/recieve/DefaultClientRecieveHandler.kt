package pusha2.client.handler.recieve

import pusha2.domain.SockDto
import pusha2.client.handler.recieve.excutor.ClientRecieveExcutor
import pusha2.client.handler.recieve.excutor.NoticeExcutor

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