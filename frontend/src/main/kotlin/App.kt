import components.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import react.dom.h3

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div {
            h2 { +"a little kotlin JS demo with react, styled, components, material-ui" }
            h3 { +"nothing fancy looking, just to showcase functionality" }
            exampleFunctionalComponentWithProps { name = "KotlinJS" }
            exampleFunctionalComponentWithStateHook
            exampleFunctionalComponentWithStyle
            exampleFunctionalComponentWithReusableStyle
            exampleComponentUsingMuirwik
        }
    }
}

fun RBuilder.app() = child(App::class) {}
