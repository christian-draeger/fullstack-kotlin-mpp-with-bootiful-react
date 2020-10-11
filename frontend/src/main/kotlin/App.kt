import MySocketMessage.StartTimeSending
import MySocketMessage.StopTimeSending
import clients.BackendWebSocketClient
import clients.fetchFromBackendExample
import components.exampleComponentUsingMuirwik
import components.exampleFunctionalComponentWithProps
import components.exampleFunctionalComponentWithReusableStyle
import components.exampleFunctionalComponentWithStateHook
import components.exampleFunctionalComponentWithStyle
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.div
import react.dom.h2
import react.dom.h3
import react.dom.p
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
    var day: Int = 1,
    var month: Int = 1,
    var year: Int = 0,
    var city: String = "",
    var country: String = ""
)

class App : RComponent<RProps, AppState>() {

    private val socketClient = BackendWebSocketClient()

    override fun AppState.init() {
        // set default state
        loading = true
        greeter = Greeter("")
        serverState = ServerState()
    }

    private fun applyServerStateToAppState(serverState: ServerState) {
        setState {
            this.serverState = serverState
        }
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
        socketClient.createSocket(state.serverState, ::applyServerStateToAppState)
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
                button {
                    +"start"
                    attrs {
                        onClickFunction = { socketClient.send(StartTimeSending) }
                    }
                }
                button {
                    +"stop"
                    attrs {
                        onClickFunction = { socketClient.send(StopTimeSending) }
                    }
                }
                p { +"hour:   ${state.serverState.hour}" }
                p { +"minute: ${state.serverState.minute}" }
                p { +"second: ${state.serverState.second}" }
                p { +"day:    ${state.serverState.day}" }
                p { +"month:  ${state.serverState.month}" }
                p { +"year:   ${state.serverState.year}" }
                p { +"city:   ${state.serverState.city}" }
                p { +"country:${state.serverState.country}" }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
