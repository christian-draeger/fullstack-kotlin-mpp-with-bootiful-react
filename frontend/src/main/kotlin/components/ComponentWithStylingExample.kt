package components

import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.css.padding
import kotlinx.css.px
import react.*
import styled.css
import styled.styledDiv

private val component = functionalComponent<RProps> {
    styledDiv {
        css {
            padding(vertical = 16.px)
            backgroundColor = Color.green
        }
        +"wow i have been styled with styled components"
    }
}

val RBuilder.exampleFunctionalComponentWithStyle
    get() = child(component)
