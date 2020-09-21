import clients.fetchFromBackendExample
import components.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.div
import react.dom.h2
import react.dom.h3

interface AppState: RState {
    var greeter: Greeter
}

class App : RComponent<RProps, AppState>() {

    override fun AppState.init() {
        greeter = Greeter("")
    }

    override fun componentDidMount() {
        MainScope().launch {
            val greeterResponse = fetchFromBackendExample("Chris")
            setState {
                greeter = greeterResponse
            }
        }
    }

    override fun RBuilder.render() {
        div {
            h2 { +"a little kotlin JS demo with react, styled, components, material-ui" }
            h3 { +"nothing fancy looking, just to showcase functionality" }
            h3 { +state.greeter.hello }
            exampleFunctionalComponentWithProps { name = "KotlinJS" }
            exampleFunctionalComponentWithStateHook
            exampleFunctionalComponentWithStyle
            exampleFunctionalComponentWithReusableStyle
            exampleComponentUsingMuirwik
        }
    }
}

fun RBuilder.app() = child(App::class) {}
