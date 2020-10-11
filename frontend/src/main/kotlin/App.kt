
import MySocketMessage.StartTimeSending
import MySocketMessage.StopTimeSending
import clients.BackendWebSocketClient
import clients.fetchFromBackendExample
import components.analogClock
import components.exampleComponentUsingMuirwik
import components.exampleFunctionalComponentWithProps
import components.exampleFunctionalComponentWithReusableStyle
import components.exampleFunctionalComponentWithStateHook
import components.exampleFunctionalComponentWithStyle
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import react.dom.h3
import react.dom.span
import react.setState

interface AppState : RState {
    var loading: Boolean
    var greeter: Greeter
    var serverState: ServerState
}

data class ServerState(
    var hour: Int = 0,
    var minute: Int = 0,
    var second: Int = 0,
    var day: Int = 15,
    var month: Int = 2,
    var year: Int = 2016,
    var city: String = "St. Petersburg",
    var country: String = "Russia"
)

class App : RComponent<RProps, AppState>() {

    private val socketClient = BackendWebSocketClient()

    // set default state
    override fun AppState.init() {
        loading = true
        greeter = Greeter("")
        serverState = ServerState()
    }

    override fun componentDidMount() {
        MainScope().launch {
            val greeterResponse = fetchFromBackendExample("Chris")
            delay(timeMillis = 3_000) // just to showcase state change of loader
            setState {
                greeter = greeterResponse
                loading = false
            }
        }
        socketClient.createSocket(state.serverState) {
            setState { serverState = it }
        }
    }

    override fun RBuilder.render() {
        if (state.loading) {
            span { +"loading state demo... (please wait 3seconds ^^)" }
        } else {
            div {
                h2 { +"a little kotlin JS demo with react, styled, components, material-ui, communicating over REST and WebSockets." }
                h3 { +"nothing fancy looking, just to showcase functionality" }
                h3 { +"message from backend via REST: ${state.greeter.hello}" }
                exampleFunctionalComponentWithProps { name = "KotlinJS" }
                exampleFunctionalComponentWithStateHook
                exampleFunctionalComponentWithStyle
                exampleFunctionalComponentWithReusableStyle
                exampleComponentUsingMuirwik

                analogClock {
                    serverState = state.serverState
                    startClockSync = { socketClient.send(StartTimeSending) }
                    stopClockSync = { socketClient.send(StopTimeSending) }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
