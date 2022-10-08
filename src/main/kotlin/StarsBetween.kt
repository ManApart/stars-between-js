import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import pages.*


lateinit var favicon: HTMLElement

fun main() {
    window.onload = {
        favicon = document.getElementById("favicon") as HTMLElement
        createDB()
        shipBuildView()
    }
}

fun clearSections() {
    el<HTMLElement>("main-content").innerHTML = ""
}

fun <T> el(id: String) = document.getElementById(id) as T