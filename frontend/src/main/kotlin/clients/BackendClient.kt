package clients

import Greeter
import kotlinx.browser.window
import kotlinx.coroutines.await


suspend fun fetchFromBackendExample(name: String): Greeter =
    window.fetch("/api/example?name=$name")
        .await()
        .json()
        .await()
        .unsafeCast<Greeter>()
