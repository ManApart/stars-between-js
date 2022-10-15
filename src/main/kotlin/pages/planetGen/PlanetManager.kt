package pages.planetGen

import kotlinx.browser.window
import planet.BiomeType
import planet.Planet
import planet.generation.PlanetGenerator
import planet.generation.PlanetOptions
import kotlin.js.Json
import kotlin.js.Promise

object PlanetManager {
    private var generator: PlanetGenerator? = null
    private val planets = mutableMapOf<Int, Planet>()

    fun createGenerator(): Promise<Boolean> {
        return Promise { resolve, _ ->
            if (generator != null) {
                resolve(true)
            } else {
                Promise.all(
                    BiomeType.values().map { it.fileName }
                        .map { name ->
                            val fileName = "./biomes/$name.json"
                            window.fetch(fileName)
                                .then { data -> data.json() }
                                .then { data -> name to JSON.stringify(data as Json) }
                        }.toTypedArray()
                ).then { data ->
                    generator = PlanetGenerator(data.toMap())
                    resolve(true)
                }
            }
        }
    }

    fun generatePlanet(id: Int, planetOptions: PlanetOptions) {
        planets[id] = generator!!.generatePlanet(planetOptions)
    }

    fun getPlanet(id: Int): Planet {
        return planets.getOrPut(id) { generator!!.generatePlanet(PlanetOptions()) }
    }


}