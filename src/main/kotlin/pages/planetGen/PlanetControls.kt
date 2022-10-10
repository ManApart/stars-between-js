package pages.planetGen

import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import pages.shipBuilder.shipBuildView


internal fun TagConsumer<HTMLElement>.planetControls() {
    div {
        id = "build-controls"
        h2 { +"Controls" }
        div {
            button {
                +"Ship"
                onClickFunction = {
                    shipBuildView()
                }
            }
        }
    }
}
