package pages.planetGen

import clearSections
import el
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.canvas
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import planet.PlanetManager
import planet.generation.PlanetOptions
import planet.generation.PlanetViewOptions
import uiTicker

internal val options = PlanetOptions()
internal var viewOptions = PlanetViewOptions()
private lateinit var canvas: HTMLCanvasElement

fun planetGenView() {
    val section = el<HTMLElement>("main-content")
    clearSections()
    uiTicker = {}
    section.append {
        planetControls()
        canvas {
            id = "planet-view-canvas"
        }
    }
    canvas = el("planet-view-canvas")
    PlanetManager.generatePlanet(0, options)
    canvas.drawPlanet(viewOptions)
}