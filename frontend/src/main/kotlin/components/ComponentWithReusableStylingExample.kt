package components

import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.Cursor
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.cursor
import kotlinx.css.display
import kotlinx.css.justifyContent
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import styled.StyleSheet
import styled.css
import styled.styledDiv

object ComponentStyles : StyleSheet("ComponentStyles") {
    val element by css {
        backgroundColor = Color.green

        hover {
            backgroundColor = Color.red
            cursor = Cursor.pointer
        }
    }
    // Example of a ".wrapper:hover .inner" selector
    val wrapper by css {
        display = Display.flex
        alignItems = Align.center
        justifyContent = JustifyContent.center
    }

    val inner by css {
        backgroundColor = Color.green

        // Use reflection to refer to other elements, it's longer but safer than using hard-coded class names
        ancestorHover("${ComponentStyles.name}-${ComponentStyles::wrapper.name}") {
            backgroundColor = Color.red
        }
    }
}

private val component = functionalComponent<RProps> {
    styledDiv {
        css {
            +ComponentStyles.element
        }
        +"wow i have been styled with a reusable styling approach"
    }
    styledDiv {
        css {
            +ComponentStyles.wrapper
        }

        styledDiv {
            css {
                +ComponentStyles.inner
            }

            +"the inner element"
        }
    }
}

val RBuilder.exampleFunctionalComponentWithReusableStyle
    get() = child(component)
