import game.Game
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import pages.planetGen.planetGenView
import pages.shipBuilder.shipBuildView

const val tickRate = 300
const val tileSize = 80
var uiTicker: () -> Unit = {}

fun main() {
    window.onload = {
        createDB()
        loadMemory().then {
//            shipBuildView()
            planetGenView()
            window.setInterval(::tick, tickRate)
        }
    }
}

private fun tick() {
    Game.tick()
    uiTicker()
}

fun clearSections() {
    el<HTMLElement>("main-content").innerHTML = ""
    uiTicker = {}
}

fun <T> el(id: String) = document.getElementById(id) as T