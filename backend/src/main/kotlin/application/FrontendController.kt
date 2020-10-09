package application

import kotlinx.html.ScriptType
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.id
import kotlinx.html.meta
import kotlinx.html.noScript
import kotlinx.html.script
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FrontendController {

    @GetMapping
    fun serveFrontend() = createHTMLDocument().html {
        head {
            meta { charset = "UTF-8" }
            script {
                type = ScriptType.textJavaScript
                src = "/frontend.js"
            }
        }
        body {
            div {
                id = "root"
                noScript { +"You need to activate JavaScript to see this page!" }
            }
        }
    }.serialize()
}
