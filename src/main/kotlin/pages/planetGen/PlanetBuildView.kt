package pages.planetGen

import clearSections
import el
import kotlinx.browser.window
import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onMouseDownFunction
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent
import planet.Region
import planet.generation.PlanetOptions
import planet.generation.PlanetViewOptions
import uiTicker
import kotlin.math.roundToInt

internal var options = PlanetOptions()
internal var viewOptions = PlanetViewOptions()
private lateinit var canvas: HTMLCanvasElement

fun planetGenView() {
    val section = el<HTMLElement>("main-content")
    clearSections()
    uiTicker = {}
    section.append {
        planetControls()
        div {
            id = "planet-view-canvas-div"
            canvas {
                id = "planet-view-canvas"
                onMouseDownFunction = { event ->
                    val e = event as MouseEvent
                    clickPlanet(e.offsetX.toInt(), e.offsetY.toInt())
                }
            }
        }
        div {
            id = "planet-region-info"
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

private fun clickPlanet(x: Int, y: Int) {
    val planet = PlanetManager.getPlanet(0)
    val region = planet.regions[x][y]
    val parent = el<HTMLElement>("planet-region-info")
    parent.innerHTML = ""
    parent.append { planetRegionInfo(x, y, region) }
}


private fun TagConsumer<HTMLElement>.planetRegionInfo(x: Int, y: Int, region: Region) {
    div {
        +"Region $x, $y"
        table {
            regionInfoRow("Altitude", region.altitude)
            regionInfoRow("Temperature", region.temperature)
            regionInfoRow("Precipitation", region.precipitation)
            regionInfoRow("Biome Name", region.biome.name)
            regionInfoRow("Biome Altitude", region.biome.altitude)
            regionInfoRow("Biome Temperature", region.biome.temperature)
            regionInfoRow("Biome Precipitation", region.biome.precipitation)
        }
    }
}

private fun TABLE.regionInfoRow(name: String, value: Number) = regionInfoRow(name, value.toString())
private fun TABLE.regionInfoRow(name: String, value: String) {
    tr {
        td { +name }
        td { +value }
    }
}