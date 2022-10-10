package pages.planetGen

import clearSections
import el
import kotlinx.html.dom.append
import org.w3c.dom.HTMLElement
import uiTicker

fun planetGenView() {
    val section = el<HTMLElement>("main-content")
    clearSections()
    section.append {
        planetControls()
    }
    uiTicker = {}
}