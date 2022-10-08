package pages

import clearSections
import el
import favicon
import game.Game
import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.append


fun shipBuildView() {
    val section = el("main-content")
    clearSections()
    document.title = "Build Ship"
    favicon.setAttribute("href", "favicon.png")
    section.append {
        div {
            +"Build Ship"
            table {
                Game.ship.floorPlan.getAllTiles().chunked(Game.ship.floorPlan.size).forEach { row ->
                    tr {
                        row.forEach { tile ->
                            td {
                                img {
                                    classes = setOf("tile-image", "rotate-${tile.rotation}")
                                    src = tile.getTileImage()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
