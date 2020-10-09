package components

import com.ccfraser.muirwik.components.MColor
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import react.RBuilder
import react.RProps
import react.child
import react.dom.div
import react.functionalComponent

private val component = functionalComponent<RProps> {
    div {
        mButton("I'm a material-UI component", variant = MButtonVariant.contained, color = MColor.primary)
    }
}

val RBuilder.exampleComponentUsingMuirwik
    get() = child(component)
