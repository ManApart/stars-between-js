package pages.shipBuilder

import el
import floorplan.Ship
import game.Game
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import loadMemory
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.HTMLSelectElement
import pages.planetGen.planetGenView
import persistMemory
import tile.SystemType


internal fun TagConsumer<HTMLElement>.mainControls() {
    div {
        id = "build-controls"
        h2 { +"Controls" }
        div {
            select {
                id = "view-mode-select"
                ShipViewMode.values().forEach { type ->
                    option {
                        value = type.name
                        +type.name.lowercase().capitalize()
                        selected = type == viewMode
                    }
                    onChangeFunction = {
                        viewMode = ShipViewMode.values()[el<HTMLSelectElement>("view-mode-select").selectedIndex]
                        updateSubControls()
                    }
                }
            }
        }
        div {
            button {
                +"Save"
                onClickFunction = {
                    persistMemory()
                }
            }
            button {
                +"Load"
                onClickFunction = {
                    loadMemory().then { shipBuildView() }
                }
            }
            button {
                +"Reset"
                onClickFunction = {
                    Game.ship = Ship()
                }
            }
        }
        div {
            button {
                +"Planet"
                onClickFunction = {
                    planetGenView()
                }
            }
        }
        div { id = "sub-controls" }
    }
}

internal fun updateSubControls() {
    val source = el<HTMLElement>("sub-controls")
    source.innerHTML = ""
    when (viewMode) {
        in listOf(ShipViewMode.BUILD, ShipViewMode.POWER, ShipViewMode.AIR) -> source.buildControls()
        ShipViewMode.CREW -> source.crewControls()
        else -> {}
    }
}

internal fun HTMLElement.buildControls() {
    append {
        img {
            id = "current-tool"
            src = currentTool.getImage()
        }
        select {
            id = "build-palette-select"
            SystemType.values().forEach { type ->
                option {
                    value = type.name
                    +type.name.lowercase().split("_").joinToString(" ") { it.capitalize() }
                    selected = type == currentTool
                }
                onChangeFunction = {
                    currentTool = SystemType.values()[el<HTMLSelectElement>("build-palette-select").selectedIndex]

                    el<HTMLImageElement>("current-tool").src = currentTool.getImage()
                }
            }
        }
    }
}

internal fun HTMLElement.crewControls() {
    append {
        button {
            +"Add"
            onClickFunction = {
                val man = Game.ship.addCrewMan()
                el<HTMLDivElement>("floorPlan-view").append {
                    addCrewman(man)
                }
                crewToDiv[man] = el("crewman-${man.id}")
            }
        }
        button {
            +"Remove"
            onClickFunction = {
                selectedCrewman?.let { man ->
                    Game.ship.removeCrewMan(man.id)
                    val dir = crewToDiv[man]
                    dir?.parentElement?.removeChild(dir)
                    crewToDiv.remove(man)
                }
            }
        }
    }
}
