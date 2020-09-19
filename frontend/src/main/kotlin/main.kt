import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.*

fun main() {
    window.onload = {
        render(document.getElementById("root")) { app() }
    }
}
