import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed class MySocketMessage {

    @Serializable
    data class ServerTime(
        var formattedDate: String,
        var formatedTime: String,
        var location: String
    ): MySocketMessage()

    @Serializable
    object MessageWithoutData : MySocketMessage()

    fun toJson() = Json.encodeToString(serializer(), this)

    companion object {
        fun parse(input: String): MySocketMessage = Json.decodeFromString(serializer(), input)
    }
}
