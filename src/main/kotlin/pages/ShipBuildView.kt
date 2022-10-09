package pages

import clearSections
import el
import favicon
import floorplan.FloorPlan
import floorplan.Position
import floorplan.Ship
import game.Game
import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import loadMemory
import move
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLSpanElement
import persistMemory
import systems.shields.Shield
import tile.SystemType
import tile.Tile
import tile.getDefault
import uiTicker

private var viewMode = ShipViewMode.BUILD
private var currentTool = SystemType.WIRE_FLOOR
private var tileToImage = mutableMapOf<Tile, HTMLImageElement>()
private var tileToText = mutableMapOf<Tile, HTMLSpanElement>()

fun shipBuildView() {
    val section = el<HTMLElement>("main-content")
    clearSections()
    document.title = "Build Ship"
    favicon.setAttribute("href", "favicon.png")
    section.append {
        buildControls()
        floorPlanView()
    }
    buildTileMap()
    uiTicker = ::paintShipView
}

private fun TagConsumer<HTMLElement>.buildControls() {
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
                    }
                }
            }
        }
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
    }
}

fun buildTileMap() {
    val tiles = Game.ship.floorPlan.getAllTiles()
    tileToImage = tiles.associateWith { tile -> el<HTMLImageElement>("tile-${tile.position.x}-${tile.position.y}-image") }.toMutableMap()
    tileToText = tiles.associateWith { tile -> el<HTMLSpanElement>("tile-${tile.position.x}-${tile.position.y}-text") }.toMutableMap()
}

private fun paintShipView() {
    when (viewMode) {
        ShipViewMode.AIR -> paintAir()
        ShipViewMode.CREW -> paintCrew()
        ShipViewMode.DISTANCE -> paintDistance()
        ShipViewMode.POWER -> paintPower()
        ShipViewMode.SHIELDS -> paintShields()
        else -> tileToText.entries.forEach { (_, text) -> text.innerText = "" }
    }
}

private fun paintAir() {
    tileToText.entries.forEach { (tile, text) ->
        if (tile.air > 0) {
            text.innerText = "" + tile.air
        } else {
            text.innerText = ""
        }
    }
    tileToImage.entries.forEach { (tile, image) ->
        if (tile.system.type != SystemType.SPACE && !tile.system.isSolid() && tile.air < 50) {
            image.style.opacity = "" + ((50 + tile.air) / 100.0)
        } else {
            image.style.opacity = "1"
        }
    }
}

private fun paintDistance() {
    tileToText.entries.forEach { (tile, text) ->
        if (tile.distanceFromSelected != Int.MAX_VALUE) {
            text.innerText = "" + tile.distanceFromSelected
        } else {
            text.innerText = ""
        }
    }
}

private fun paintCrew() {

}

private fun paintPower() {

}

private fun paintShields() {
    tileToText.entries.forEach { (tile, text) ->
        if (tile.system is Shield) {
            text.innerText = "" + Game.ship.floorPlan.getId(tile.system)
        } else text.innerText = ""
    }
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

        ShipViewMode.DISTANCE -> {
            setSelectedTile(tile)
        }

        else -> println("Clicked ${tile.position}")
    }
}


private fun repaintTileImages(source: Position) {
    listOf(source, source.up(), source.down(), source.left(), source.right())
        .map { Game.ship.floorPlan.getTile(it) }
        .forEach { tile ->
            with(tileToImage[tile]!!) {
                src = tile.getTileImage()
                classList.value = "tile-image rotate-${tile.rotation}"
            }
        }

}

