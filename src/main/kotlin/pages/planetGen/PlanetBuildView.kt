package pages.planetGen

import clearSections
import el
import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.canvas
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
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
    planetSphere()
    PlanetManager.createGenerator().then {
        generateAndDisplayPlanet()
    }
}

fun generateAndDisplayPlanet() {
    PlanetManager.generatePlanet(0, options)
    displayPlanet()
}

fun displayPlanet() {
    val planet = PlanetManager.getPlanet(0)
    val size = planet.regions.size
    canvas.setAttribute("width", "${size}px")
    canvas.setAttribute("height", "${size}px")
    (canvas.getContext("2d") as CanvasRenderingContext2D).drawPlanet(planet, viewOptions)
}

fun planetSphere() {
    if (viewOptions.sphere) canvas.addClass("circle") else canvas.removeClass("circle")
}