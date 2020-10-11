
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed class MySocketMessage {

    @Serializable
    data class ServerTime(
        var hour: Int,
        var minute: Int,
        var second: Int
    ) : MySocketMessage()

    @Serializable
    data class ServerDate(
        var day: Int,
        var month: Int,
        var year: Int
    ) : MySocketMessage()

    @Serializable
    data class ServerLocation(
        var city: String,
        var country: String,
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
