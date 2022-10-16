package pages.planetGen

import el
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLLabelElement
import pages.checkBoxDiv
import pages.dropDown
import pages.rangeTableRow
import pages.shipBuilder.shipBuildView
import planet.BiomeType
import planet.generation.PlanetOptions
import random
import kotlin.random.Random


internal fun TagConsumer<HTMLElement>.planetControls() {
    div {
        id = "build-controls"
        h2 { +"Controls" }
        viewControls()
        div {
            id = "generation-controls"
            generationControls()
        }
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

private fun TagConsumer<HTMLElement>.viewControls() {
    dropDown(viewOptions::viewType, PlanetViewType.values().toList(), ::displayPlanet)
    checkBoxDiv("Sphere", viewOptions::sphere, ::planetSphere)
    checkBoxDiv("Shadow", viewOptions::shadow, ::displayPlanet)
}

private fun TagConsumer<HTMLElement>.generationControls() {
    checkBoxDiv("Auto Update", viewOptions::autoUpdate)

    dropDown(options::biomeType, BiomeType.values().toList(), ::displayPlanet)

    table {
        id = "planet-controls-table"
        rangeTableRow("Seed", options::seed, 0, 10000, 1, ::autoGen)
        rangeTableRow("Density", options::density, 1, 500, 1, ::autoGen)
        rangeTableRow("Octaves", options::octaves, 1, 10, 1, ::autoGen)
        rangeTableRow("Roughness", options::roughness, 0, 2, 1, ::autoGen)
        rangeTableRow("Noise Scale", options::noiseScale, 1, 10, 1, ::autoGen)
        rangeTableRow("Temperature", options::temperature, -200, 1000, 10, ::autoGen)
        rangeTableRow("Temperature Variance", options::temperatureVariance, 0, 500, 1, ::autoGen)
        rangeTableRow("Temperature Factor", options::temperatureFactor, 1, 5, 1, ::autoGen)
        rangeTableRow("Default Precipitation", options::defaultPrecipitation, 0, 500, 1, ::autoGen)
        rangeTableRow("Water Threshold", options::waterThreshold, -200, 1000, 1, ::autoGen)
    }

    div {
        button {
            +"Random Seed"
            onClickFunction = {
                options.seed = random.nextLong(0, 10000)
                el<HTMLInputElement>("seed-range").value = "${options.seed}"
                el<HTMLLabelElement>("seed-range-cell").innerText = "${options.seed}"
                generateAndDisplayPlanet()
            }
        }
    }
    div {
        button {
            +"Reset"
            onClickFunction = {
                options = PlanetOptions()
                val parent = el<HTMLElement>("generation-controls")
                parent.innerHTML = ""
                parent.append { generationControls() }
                generateAndDisplayPlanet()
            }
        }
    }
    div {
        button {
            +"Generate"
            onClickFunction = {
                generateAndDisplayPlanet()
            }
        }
    }
}

private fun autoGen() {
    if (viewOptions.autoUpdate) generateAndDisplayPlanet()
}