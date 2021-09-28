package pusha2.client.handler.exception

import pusha2.client.ClientManager
import pusha2.container.ClientContainer
import pusha2.util.Log.PushaLog
import pusha2.util.socket.WrappedSocket
import javax.swing.JOptionPane


class DefaultClientSocketExceptionHandler : ClientSocketExceptionHandler {

    override fun connectionFaild() {
        PushaLog.log("Failed to connect to pusha2.server.")

        val clientManager: ClientManager = ClientContainer.clientManager()
        clientManager.socket = null
    }

    override fun connectionLost(wrappedSocket: WrappedSocket) {
        PushaLog.log("My id: ${wrappedSocket.id}, Disconnected from the pusha2.server.")

        val clientManager: ClientManager = ClientContainer.clientManager()
        clientManager.socket = null
    }

    override fun ipInputNotNumber() {
        JOptionPane.showMessageDialog(null, "Enter number in the Ip.")
    }
}