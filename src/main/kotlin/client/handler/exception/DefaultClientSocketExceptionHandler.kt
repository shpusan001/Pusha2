package dekilla.core.client.handler.exception

import dekilla.core.client.ClientManager
import dekilla.core.container.ClientContainer
import dekilla.core.util.Log.PushaLog
import dekilla.core.util.socket.WrappedSocket
import javax.swing.JOptionPane
import kotlin.system.exitProcess


class DefaultClientSocketExceptionHandler : ClientSocketExceptionHandler {

    override fun connectionFaild() {
        PushaLog.log("Failed to connect to server.")

        val clientManager:ClientManager = ClientContainer.clientManager()
        clientManager.socket = null
    }

    override fun connectionLost(wrappedSocket: WrappedSocket) {
        PushaLog.log("My id: ${wrappedSocket.id}, Disconnected from the server.")

        val clientManager:ClientManager = ClientContainer.clientManager()
        clientManager.socket = null
    }

    override fun ipInputNotNumber() {
        JOptionPane.showMessageDialog(null, "Enter number in the Ip.")
    }
}