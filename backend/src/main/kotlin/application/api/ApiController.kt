package application.api

import Greeter
import MySocketMessage
import kotlinx.datetime.Clock
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api")
class RestApiController {

    @GetMapping("/example")
    fun getSomeData(
        @RequestParam name: String
    ) = Greeter("hello $name")
}

@Component
class SocketHandler : TextWebSocketHandler() {

    public fun send(message: MySocketMessage) {
        val deserialized = message.toJson()
        sessionState.values.forEach {
            it.session.sendMessage(TextMessage(deserialized))
        }
    }

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = MySocketMessage.parse(message.payload)
        if (payload is MySocketMessage.StartTimeSending) {
            sessionState[session.id]?.time = true
            sendOnDataTimeChange(session)
        }
        if (payload is MySocketMessage.StopTimeSending) {
            sessionState[session.id]?.time = false
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessionState[session.id] = State(session.id, session, false)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info { "websocket closed" }
    }

    var currentDateAndTimeState = ""
    fun sendOnDataTimeChange(session: WebSocketSession) {
        thread {
            while (sessionState[session.id]?.time == true) {
                val time = Clock.System.now().toString()
                if (currentDateAndTimeState != time) {
                    currentDateAndTimeState = time
                    session.sendMessage(TextMessage(MySocketMessage.ServerTime(time, time, "Berlin").toJson()))
                }
            }
        }
    }
}

var sessionState = ConcurrentHashMap<String, State>()

data class State(
    val id: String,
    val session: WebSocketSession,
    var time: Boolean = false
)
