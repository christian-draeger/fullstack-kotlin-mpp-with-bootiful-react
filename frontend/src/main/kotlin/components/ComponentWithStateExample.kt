package components

import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.child
import react.dom.button
import react.functionalComponent
import react.getValue
import react.setValue
import react.useState

private val component = functionalComponent<RProps> {
    var count by useState(0)
    button {
        attrs.onClickFunction = { count += 1 }
        +"You clicked me babe ${count.withPluralTense()}"
    }
}

private fun Int.withPluralTense() = when (this) {
    0 -> "0 times"
    1 -> "1 more time"
    else -> "$this more times"
}

val RBuilder.exampleFunctionalComponentWithStateHook
    get() = child(component) {}
