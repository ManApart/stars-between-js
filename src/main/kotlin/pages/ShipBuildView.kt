package pages

import clearSections
import crew.CrewMan
import el
import favicon
import floorplan.FloorPlan
import floorplan.Position
import game.Game
import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onClickFunction
import move
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.HTMLSpanElement
import power.Engine
import power.Powerable
import systems.shields.Shield
import tile.SystemType
import tile.Tile
import tile.getDefault
import tileSize
import uiTicker

internal var viewMode = ShipViewMode.CREW
internal var currentTool = SystemType.WIRE_FLOOR
internal var selectedCrewman: CrewMan? = null
internal var tileToImage = mutableMapOf<Tile, HTMLImageElement>()
internal var tileToText = mutableMapOf<Tile, HTMLSpanElement>()
internal var crewToDiv = mutableMapOf<CrewMan, HTMLDivElement>()

fun shipBuildView() {
    val section = el<HTMLElement>("main-content")
    clearSections()
    document.title = "Build Ship"
    favicon.setAttribute("href", "favicon.png")
    section.append {
        mainControls()
        floorPlanView()
    }
    updateSubControls()
    buildTileMap()
    uiTicker = ::paintShipView
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
                                id = "tile-${tile.position.x}-${tile.position.y}-image"
                                classes = setOf("tile-image", "rotate-${tile.rotation}")
                                src = tile.getTileImage()
                            }
                            span("tile-text") {
                                id = "tile-${tile.position.x}-${tile.position.y}-text"
                            }
                            onClickFunction = {
                                plan.tileClicked(tile.position)
                            }
                        }
                    }
                }
            }
        }
        Game.ship.crew.values.forEach { man ->
            addCrewman(man)
        }
    }
}

internal fun TagConsumer<HTMLElement>.addCrewman(man: CrewMan) {
    div("crewman") {
        id = "crewman-${man.id}"
        val pos = man.pixelPosition()
        style = "left: ${pos.x}px; top: ${pos.y}px; background-color: ${man.division.color};"
        onClickFunction = { Game.ship.floorPlan.tileClicked(man.tile.position) }
    }
}

fun buildTileMap() {
    val tiles = Game.ship.floorPlan.getAllTiles()
    tileToImage = tiles.associateWith { tile -> el<HTMLImageElement>("tile-${tile.position.x}-${tile.position.y}-image") }.toMutableMap()
    tileToText = tiles.associateWith { tile -> el<HTMLSpanElement>("tile-${tile.position.x}-${tile.position.y}-text") }.toMutableMap()
    crewToDiv = Game.ship.crew.values.associateWith { man -> el<HTMLDivElement>("crewman-${man.id}") }.toMutableMap()
}


private fun FloorPlan.tileClicked(position: Position) {
    val tile = getTile(position)
    when (viewMode) {
        in listOf(ShipViewMode.BUILD, ShipViewMode.AIR) -> {
            setTile(getDefault(currentTool), tile.position.x, tile.position.y)
            val newTile = getTile(tile.position)
            tileToImage[tile]?.src = newTile.getTileImage()
            tileToText.move(tile, newTile)
            tileToImage.move(tile, newTile)
            repaintTileImages(position)
        }

        in listOf(ShipViewMode.DISTANCE, ShipViewMode.CREW) -> {
            tileToText[getSelectedTile()]?.removeClass("selected-tile")
            setSelectedTile(tile)
            tileToText[getSelectedTile()]?.addClass("selected-tile")
        }

        else -> println("Clicked ${tile.position}")
    }
}




