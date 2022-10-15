package pages.planetGen

import el
import kotlinx.html.*
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement
import pages.shipBuilder.shipBuildView


internal fun TagConsumer<HTMLElement>.planetControls() {
    div {
        id = "build-controls"
        h2 { +"Controls" }
        div {
            select {
                id = "planet-view-select"
                PlanetViewType.values().forEach { type ->
                    option {
                        value = type.name
                        +type.name.lowercase().split("_").joinToString(" ") { it.capitalize() }
                        selected = type == viewOptions.viewType
                    }
                    onChangeFunction = {
                        viewOptions.viewType = PlanetViewType.values()[el<HTMLSelectElement>("planet-view-select").selectedIndex]
                        generateAndDisplayPlanet()
                    }
                }
            }
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
