package components

import react.*
import react.dom.h1

external interface WelcomeProps : RProps {
    var name: String
}

private val component = functionalComponent<WelcomeProps> { props ->
    h1 { +"welcome ${props.name}" }
}

fun RBuilder.exampleFunctionalComponentWithProps(handler: WelcomeProps.() -> Unit) = child(component) {
    attrs { handler() }
}
