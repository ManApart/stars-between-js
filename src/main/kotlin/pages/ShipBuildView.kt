package pages

import clearSections
import el
import favicon
import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.append


fun shipBuildView() {
    val section = el("main-content")
    clearSections()
    document.title = "Build Ship"
    favicon.setAttribute("href", "favicon.png")
    section.append {
        div {
            +"Build Ship"
        }
    }
}
