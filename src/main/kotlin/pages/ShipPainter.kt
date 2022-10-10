package pages

import crew.CrewMan
import floorplan.Position
import game.Game
import kotlinx.dom.removeClass
import power.Engine
import power.Powerable
import systems.shields.Shield
import tile.SystemType
import tileSize

private var previousView = viewMode

internal fun paintShipView() {
    if (viewMode != previousView) {
        previousView = viewMode
        clearStyles()
    }
    when (viewMode) {
        ShipViewMode.AIR -> paintAir()
        ShipViewMode.CREW -> {
            paintCrew()
            paintCrewmen()
        }
        ShipViewMode.DISTANCE -> paintDistance()
        ShipViewMode.POWER -> paintPower()
        ShipViewMode.SHIELDS -> paintShields()
        else -> {}
    }

}

fun clearStyles() {
    tileToText.entries.forEach { (_, text) ->
        text.innerText = ""
        text.removeClass("selected-tile")
    }
    tileToImage.entries.forEach { (_, image) ->
        image.style.opacity = "1"
    }
}

internal fun paintAir() {
    tileToText.entries.forEach { (tile, text) ->
        if (tile.air > 0) {
            text.innerText = "" + tile.air
        }
    }
    tileToImage.entries.forEach { (tile, image) ->
        if (tile.system.type != SystemType.SPACE && !tile.system.isSolid() && tile.air < 50) {
            image.style.opacity = "" + ((50 + tile.air) / 100.0)
        }
    }
}

internal fun paintDistance() {
    tileToText.entries.forEach { (tile, text) ->
        if (tile.distanceFromSelected != Int.MAX_VALUE) {
            text.innerText = "" + tile.distanceFromSelected
        }
    }
}

internal fun paintCrew() {
    selectedCrewman?.goal?.let {
        tileToText[it]?.innerText = "G"
    }
}

internal fun paintPower() {
    tileToText.entries.forEach { (tile, text) ->
        if (tile.system.type == SystemType.ENGINE) {
            val engine = tile.system as Engine
            text.innerText = engine.power.toString()
        } else if (tile.system is Powerable) {
            val system = tile.system as Powerable
            text.innerText = system.power.toString()
        }
    }
}

internal fun paintShields() {
    tileToText.entries.forEach { (tile, text) ->
        if (tile.system is Shield) {
            text.innerText = "" + Game.ship.floorPlan.getId(tile.system)
        }
    }
}

internal fun repaintTileImages(source: Position) {
    listOf(source, source.up(), source.down(), source.left(), source.right())
        .map { Game.ship.floorPlan.getTile(it) }
        .forEach { tile ->
            with(tileToImage[tile]!!) {
                src = tile.getTileImage()
                classList.value = "tile-image rotate-${tile.rotation}"
            }
        }
}

private fun paintCrewmen() {
    crewToDiv.entries.forEach { (man, div) ->
        val pos = man.pixelPosition()
        div.style.left = "" + pos.x
        div.style.top = "" + pos.y
    }
}

fun CrewMan.pixelPosition(): Position {
    return Position(tileSize * tile.position.x + tileSize / 4, tileSize * tile.position.y + tileSize / 2)
}