package application

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
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