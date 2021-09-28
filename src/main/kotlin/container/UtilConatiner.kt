package dekilla.core.container

import dekilla.core.util.socket.SocketUtil

class UtilConatiner {

    companion object {
        private var socketUtil: SocketUtil? = null


        fun socketUtil(): SocketUtil {
            if (socketUtil == null)
                socketUtil = SocketUtil()
            return socketUtil!!
        }
    }
}