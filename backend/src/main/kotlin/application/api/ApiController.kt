package application.api

import Greeter
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

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

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        logger.debug { "session: $session" }
        logger.debug { "message: $message" }
        val payload = MySocketMessage.parse(message.payload)
        session.sendMessage(TextMessage("Hi $payload how may we help you?"))
    }
}
