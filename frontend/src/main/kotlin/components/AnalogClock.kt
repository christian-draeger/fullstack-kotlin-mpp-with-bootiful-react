package components

import ServerState
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.FontWeight
import kotlinx.css.Position.absolute
import kotlinx.css.Position.relative
import kotlinx.css.TextAlign
import kotlinx.css.background
import kotlinx.css.border
import kotlinx.css.borderRadius
import kotlinx.css.borderTopLeftRadius
import kotlinx.css.borderTopRightRadius
import kotlinx.css.bottom
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.fontWeight
import kotlinx.css.height
import kotlinx.css.left
import kotlinx.css.letterSpacing
import kotlinx.css.lineHeight
import kotlinx.css.margin
import kotlinx.css.marginLeft
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.LineHeight
import kotlinx.css.px
import kotlinx.css.right
import kotlinx.css.textAlign
import kotlinx.css.top
import kotlinx.css.width
import kotlinx.css.zIndex
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.child
import react.dom.button
import react.dom.div
import react.functionalComponent
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

object AnalogClockStyles : StyleSheet("AnalogClockStyles") {
    val clock by css {
        background = "#ececec"
        width = 300.px
        height = 300.px
        margin = "8% auto 0"
        borderRadius = 50.pct
        border = "14px solid #333"
        position = relative
        put("box-shadow", "0 2vw 4vw -1vw rgba(0,0,0,0.8)")
    }

    val dot by css {
        width = 14.px
        height = 14.px
        borderRadius = 50.pct
        background = "#ccc"
        top = 0.px
        left = 0.px
        right = 0.px
        bottom = 0.px
        margin = "auto"
        position = absolute
        zIndex = 10
        put("box-shadow", "0 2px 4px -1px black")
    }

    val info by css {
        position = absolute
        width = 120.px
        height = 20.px
        borderRadius = 7.px
        background = "#ccc"
        textAlign = TextAlign.center
        lineHeight = LineHeight("20px")
        color = Color.black
        fontSize = 11.px
        top = 200.px
        left = 50.pct
        marginLeft = (-60).px
        fontFamily = "Poiret One"
        fontWeight = FontWeight.w700
        zIndex = 3
        letterSpacing = 3.px
    }

    val date by css {
        top = 80.px
    }
    val city by css {
        top = 200.px
    }

    val hourHand by css {
        position = absolute
        zIndex = 5
        width = 4.px
        height = 65.px
        background = "#333"
        top = 79.px
        left = 50.pct
        marginLeft = (-2).px
        borderTopLeftRadius = 50.pct
        borderTopRightRadius = 50.pct
        put("transformOrigin", " 50% 72px")
    }

    val minuteHand by css {
        position = absolute
        zIndex = 6
        width = 4.px
        height = 100.px
        background = "#666"
        top = 46.px
        left = 50.pct
        marginLeft = (-2).px
        borderTopLeftRadius = 50.pct
        borderTopRightRadius = 50.pct
        put("transformOrigin", "50% 105px")
    }

    val secondHand by css {
        position = absolute
        zIndex = 7
        width = 2.px
        height = 120.px
        background = Color.red.value
        top = 26.px
        left = 50.pct
        marginLeft = (-1).px
        borderTopLeftRadius = 50.pct
        borderTopRightRadius = 50.pct
        put("transformOrigin", " 50% 125px")
    }

    val span by css {
        display = Display.inlineBlock
        position = absolute
        color = Color("#333")
        fontSize = 22.px
        fontFamily = "Poiret One"
        fontWeight = FontWeight.w700
        zIndex = 4
    }

    val hour12 by css {
        top = 30.px
        left = 50.pct
        marginLeft = (-9).px
    }
    val hour3 by css {
        top = 140.px
        right = 30.px
    }
    val hour6 by css {
        bottom = 30.px
        left = 50.pct
        marginLeft = (-5).px
    }
    val hour9 by css {
        left = 32.px
        top = 140.px
    }

    val dialLines by css {
        position = absolute
        zIndex = 2
        width = 2.px
        height = 15.px
        background = "#666"
        left = 50.pct
        marginLeft = (-1).px
        put("transformOrigin", " 50% 150px")

        nthOfType("5n") {
            width = 4.px
            height = 25.px
        }
    }
}

external interface AnalogClockProps : RProps {
    var serverState: ServerState
    var startClockSync: () -> Unit
    var stopClockSync: () -> Unit
}

private val component = functionalComponent<AnalogClockProps> { props ->

    val hourHandDeg = props.serverState.hour * 30 + props.serverState.minute * (360 / 720)
    val minuteHandDeg = props.serverState.minute * 6 + props.serverState.second * (360 / 3600)
    val secondHandDeg = props.serverState.second * 6

    styledDiv {
        css { +AnalogClockStyles.clock }

        div {
            styledDiv {
                css { +AnalogClockStyles.info }
                css { +AnalogClockStyles.date }
                +"${props.serverState.day}.${props.serverState.month}.${props.serverState.year}"
            }
            styledDiv {
                css { +AnalogClockStyles.info }
                css { +AnalogClockStyles.city }
                +props.serverState.city
            }
        }
        styledDiv {
            css {
                +AnalogClockStyles.dot
            }
        }
        div {
            styledDiv {
                css {
                    +AnalogClockStyles.hourHand
                    put("transform", "rotate(${hourHandDeg}deg)")
                }
            }
            styledDiv {
                css {
                    +AnalogClockStyles.minuteHand
                    put("transform", "rotate(${minuteHandDeg}deg)")
                }
            }
            styledDiv {
                css {
                    +AnalogClockStyles.secondHand
                    put("transform", "rotate(${secondHandDeg}deg)")
                    put("transition", "all 0.05s")
                    put("transition-timing-function", "cubic-bezier(0.1, 2.7, 0.58, 1)")
                }
            }
        }
        div {
            styledSpan {
                css { +AnalogClockStyles.span }
                css { +AnalogClockStyles.hour3 }
                +"3"
            }
            styledSpan {
                css { +AnalogClockStyles.span }
                css { +AnalogClockStyles.hour6 }
                +"6"
            }
            styledSpan {
                css { +AnalogClockStyles.span }
                css { +AnalogClockStyles.hour9 }
                +"9"
            }
            styledSpan {
                css { +AnalogClockStyles.span }
                css { +AnalogClockStyles.hour12 }
                +"12"
            }
        }

        for (i in 0..59) {
            styledDiv {
                css {
                    +AnalogClockStyles.dialLines
                    put("transform", "rotate(${6 * i}deg)")
                }
            }
        }

        button {
            +"start"
            attrs {
                onClickFunction = { props.startClockSync() }
            }
        }
        button {
            +"stop"
            attrs {
                onClickFunction = { props.stopClockSync() }
            }
        }
    }
}

fun RBuilder.analogClock(handler: AnalogClockProps.() -> Unit) = child(component) {
    attrs { handler() }
}
