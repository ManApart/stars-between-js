import game.Game
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import pages.*

const val tickRate = 300
lateinit var favicon: HTMLElement
var uiTicker: () -> Unit = {}

fun main() {
    window.onload = {
        favicon = document.getElementById("favicon") as HTMLElement
        createDB()
//        shipBuildView()
        loadMemory().then {
            shipBuildView()
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