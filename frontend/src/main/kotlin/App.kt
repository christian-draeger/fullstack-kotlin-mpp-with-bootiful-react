import MySocketMessage.*
import clients.SocketClient
import clients.fetchFromBackendExample
import components.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

interface AppState : RState {
    var loading: Boolean
    var greeter: Greeter
    var serverTime: String
}

class App : RComponent<RProps, AppState>() {

    val socketClient = SocketClient()

    override fun AppState.init() {
        // set default state
        loading = true
        greeter = Greeter("")
        serverTime = ""
    }

    override fun componentDidMount() {
        MainScope().launch {
            val greeterResponse = fetchFromBackendExample("Chris")
            delay(3000) // just to showcase state change of loader
            setState {
                greeter = greeterResponse
                loading = false
            }
        }
        socketClient.createSocket()
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
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
