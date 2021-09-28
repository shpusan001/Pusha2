package pusha2.server.repository

import pusha2.util.socket.WrappedSocket
import java.util.concurrent.CopyOnWriteArrayList

class HashmapSockRepository : SockRepository {

    private val mapRepository = HashMap<String, WrappedSocket>()
    private val listRepository = CopyOnWriteArrayList<WrappedSocket>()

    override fun add(key: String, wrappedSocket: WrappedSocket): Boolean {
        if (mapRepository.containsKey(key)) {
            mapRepository.remove(key)
            mapRepository.put(key, wrappedSocket)
            listRepository.add(wrappedSocket)
            return false
        } else {
            mapRepository.put(key, wrappedSocket)
            listRepository.add(wrappedSocket)
            return true
        }
    }

    override fun isContain(key: String): Boolean {
        return mapRepository.containsKey(key)
    }

    override fun isContain(value: WrappedSocket): Boolean {
        return mapRepository.containsValue(value)
    }

    override fun get(key: String): WrappedSocket? {
        return mapRepository.get(key)
    }

    override fun getList(): CopyOnWriteArrayList<WrappedSocket> {
        return listRepository
    }

    override fun remove(key: String): Boolean {
        if (mapRepository.containsKey(key)) {
            val willRemove = mapRepository.get(key)
            listRepository.remove(willRemove)
            mapRepository.remove(key)
            return true
        } else {
            return false
        }
    }

    override fun close() {
        mapRepository.forEach { e -> e.value.socket.close() }
        mapRepository.clear()
    }
}