
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed class MySocketMessage {

    @Serializable
    data class ServerTime(
        var formattedDate: String,
        var formattedTime: String,
        var location: String
    ) : MySocketMessage()

    @Serializable
    object StartTimeSending : MySocketMessage()

    @Serializable
    object StopTimeSending : MySocketMessage()

    fun toJson() = Json.encodeToString(serializer(), this)

    companion object {
        fun parse(input: String): MySocketMessage = Json.decodeFromString(serializer(), input)
    }
}
