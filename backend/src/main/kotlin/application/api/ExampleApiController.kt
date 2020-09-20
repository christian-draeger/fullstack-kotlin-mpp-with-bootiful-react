package application.api

import Dummy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ExampleApiController {

    @GetMapping("/example")
    fun getSomeData() = Dummy("i'm a shared model")

}
