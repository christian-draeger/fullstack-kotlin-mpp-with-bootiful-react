package application.api

import MySocketMessage
import MySocketMessage.ServerDate
import MySocketMessage.ServerLocation
import MySocketMessage.ServerTime
import MySocketMessage.StartTimeSending
import MySocketMessage.StopTimeSending
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

private val logger = KotlinLogging.logger {}

@Component
class MyWebSocketHandler : TextWebSocketHandler() {

    public fun send(message: MySocketMessage) {
        sessionState.values.forEach {
            it.session.send(message)
        }
    }

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = MySocketMessage.parse(message.payload)
        if (payload is StartTimeSending) {
            sessionState[session.id]?.sendTime = true
            sendOnDataTimeChange(session)
        }
        if (payload is StopTimeSending) {
            sessionState[session.id]?.sendTime = false
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessionState[session.id] = State(
            id = session.id,
            session = session,
            sendTime = false,
            dateTime = LocalDateTime(
                year = 0,
                monthNumber = 1,
                dayOfMonth = 1,
                hour = 0,
                minute = 0,
                second = 0,
                nanosecond = 0
            )
        )
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info { "websocket closed" }
    }

    private fun sendOnDataTimeChange(session: WebSocketSession) {

        session.send(
            ServerLocation(
                city = "Berlin",
                country = "Germany"
            )
        )

        thread {
            val currentState = sessionState[session.id]
            while (currentState?.sendTime == true) {

                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

                if (currentState.dateTime.dayOfMonth != now.dayOfMonth) {
                    session.send(
                        ServerDate(
                            day = now.dayOfMonth,
                            month = now.monthNumber,
                            year = now.year,
                        )
                    )
                }

                if (currentState.dateTime.second != now.second) {
                    currentState.dateTime = now
                    session.send(
                        ServerTime(
                            hour = currentState.dateTime.hour,
                            minute = currentState.dateTime.minute,
                            second = currentState.dateTime.second
                        )
                    )
                }
            }
        }
    }

    private fun WebSocketSession.send(message: MySocketMessage) {
        sendMessage(TextMessage(message.toJson()))
    }
}

var sessionState = ConcurrentHashMap<String, State>()

data class State(
    val id: String,
    val session: WebSocketSession,
    var sendTime: Boolean = false,
    var dateTime: LocalDateTime
)
