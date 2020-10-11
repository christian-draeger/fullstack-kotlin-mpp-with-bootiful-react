package clients

import MySocketMessage
import ServerState
import kotlinx.browser.window
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket

class BackendWebSocketClient {

    private var webSocket: WebSocket? = null

    private val host
        get() = window.location.host
    private val protocol
        get() = if (window.location.protocol == "https:") "wss:" else "ws:"

    fun createSocket(state: ServerState, applyServerStateToAppState: (ServerState) -> Unit) {
        webSocket = WebSocket("$protocol//$host/ws/frontend").apply {
            onclose = {
                println("websocket closed")
                invalidateSocket()
            }
            onmessage = {
                it.receive(state, applyServerStateToAppState)
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

    private fun MessageEvent.receive(serverState: ServerState, applyServerStateToAppState: (ServerState) -> Unit) {
        when (val message = MySocketMessage.parse(data.toString())) {
            is MySocketMessage.ServerLocation -> serverState.apply {
                city = message.city
                country = message.country
            }
            is MySocketMessage.ServerTime -> serverState.apply {
                hour = message.hour
                minute = message.minute
                second = message.second
            }
            is MySocketMessage.ServerDate -> serverState.apply {
                day = message.day
                month = message.month
                year = message.year
            }
            else -> println(message)
        }
        applyServerStateToAppState(serverState)
    }
}
