package pages

import clearSections
import el
import favicon
import game.Game
import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.HTMLParagraphElement
import org.w3c.dom.HTMLSelectElement
import power.Engine
import tile.SystemType
import tile.Tile
import tile.getDefault

private var currentTool = SystemType.ENGINE

fun shipBuildView() {
    val section = el<HTMLElement>("main-content")
    clearSections()
    document.title = "Build Ship"
    favicon.setAttribute("href", "favicon.png")
    section.append {
        buildControls()
        floorPlanView()
    }
}

private fun TagConsumer<HTMLElement>.buildControls() {
    div {
        id = "build-controls"
        h2 { +"Controls" }
        img {
            id = "current-tool"
            src = currentTool.getImage()
        }
        select {
            id = "build-palette-select"
            SystemType.values().forEach { type ->
                option {
                    value = type.name
                    +type.name.lowercase().capitalize()
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

private fun TagConsumer<HTMLElement>.floorPlanView() {
    div {
        id = "floorPlan-view"
        table {
            val plan = Game.ship.floorPlan
            plan.getAllTiles().chunked(plan.size).forEach { row ->
                tr {
                    row.forEach { tile ->
                        td {
                            img {
                                classes = setOf("tile-image", "rotate-${tile.rotation}")
                                src = tile.getTileImage()
                            }
                            onClickFunction = {
                                plan.setTile(getDefault(currentTool), tile.position.x, tile.position.y)
                                shipBuildView()
                            }
                        }
                    }
                }
            }
        }
    }
}
