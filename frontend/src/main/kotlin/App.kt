import clients.fetchFromBackendExample
import components.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import react.*
import react.dom.div
import react.dom.h2
import react.dom.h3
import react.dom.span

interface AppState: RState {
    var loading: Boolean
    var greeter: Greeter
}

class App : RComponent<RProps, AppState>() {

    override fun AppState.init() {
        // set default state
        loading = true
        greeter = Greeter("")
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
    }

    override fun RBuilder.render() {
        if (state.loading) {
            span { +"loading" }
        } else {
            div {
                h2 { +"a little kotlin JS demo with react, styled, components, material-ui" }
                h3 { +"nothing fancy looking, just to showcase functionality" }
                h3 { +"message from backend via REST: ${state.greeter.hello}" }
                exampleFunctionalComponentWithProps { name = "KotlinJS" }
                exampleFunctionalComponentWithStateHook
                exampleFunctionalComponentWithStyle
                exampleFunctionalComponentWithReusableStyle
                exampleComponentUsingMuirwik
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
