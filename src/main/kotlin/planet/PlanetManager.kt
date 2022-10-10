package planet

import planet.generation.PlanetGenerator
import planet.generation.PlanetOptions

object PlanetManager {
    private val generator = PlanetGenerator()
    private val planets = mutableMapOf<Int, Planet>()

    fun generatePlanet(id: Int, planetOptions: PlanetOptions) {
        planets[id] = generator.generatePlanet(planetOptions)
    }

    fun getPlanet(id: Int): Planet {
        return planets.getOrPut(id) { generator.generatePlanet(PlanetOptions()) }
    }
}
