package application.api

import Greeter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MyRestApiController {

    @GetMapping("/example")
    fun getSomeData(
        @RequestParam name: String
    ) = Greeter("hello $name")
}
