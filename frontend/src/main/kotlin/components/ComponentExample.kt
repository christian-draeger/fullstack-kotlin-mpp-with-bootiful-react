package components

import react.RBuilder
import react.RProps
import react.child
import react.dom.h1
import react.functionalComponent

external interface WelcomeProps : RProps {
    var name: String
}

private val component = functionalComponent<WelcomeProps> { props ->
    h1 { +"welcome ${props.name}" }
}

fun RBuilder.exampleFunctionalComponentWithProps(handler: WelcomeProps.() -> Unit) = child(component) {
    attrs { handler() }
}
