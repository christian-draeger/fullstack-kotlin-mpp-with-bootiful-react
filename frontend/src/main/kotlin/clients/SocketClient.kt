package clients

import MySocketMessage
import kotlinx.browser.window
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket

class SocketClient {

    private var webSocket: WebSocket? = null

    private val host
        get() = window.location.host
    private val protocol
        get() = if (window.location.protocol == "https:") "wss:" else "ws:"

    fun createSocket() {
        webSocket = WebSocket("$protocol//$host/ws/frontend").apply {
            onclose = {
                println("websocket closed")
                invalidateSocket()
            }
            onmessage = {
                it.receive()
            }
            onopen = {
                println("websocket open")
            }
        }
    }

    private fun invalidateSocket() {
        webSocket = null
    }

    fun send(command: MySocketMessage) {
        webSocket?.send(command.toJson())
    }

    private fun MessageEvent.receive() {
        val bla = MySocketMessage.parse(data.toString())
        println(bla)
        // TODO: write to global store
    }
}
