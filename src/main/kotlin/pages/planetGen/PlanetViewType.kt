package pages.planetGen

import planet.Planet

enum class PlanetViewType(val pixelColor: (Planet, Int, Int) -> RGB) {
    ALTITUDE(::altitude),
    BIOME(::biome),
    PRECIPITATION(::precipitation),
    TEMPERATURE(::temperature),
    SATELLITE(::satellite)
}


private fun altitude(planet: Planet, x: Int, y: Int): RGB {
    val altitude = planet.regions[x][y].altitude
    return altitudeSpectrum.getColor(altitude)
}

private fun precipitation(planet: Planet, x: Int, y: Int): RGB {
    val precipitation = planet.regions[x][y].precipitation
    return precipitationSpectrum.getColor(precipitation)
}

private fun temperature(planet: Planet, x: Int, y: Int): RGB {
    val temperature = planet.regions[x][y].temperature
    return temperatureSpectrum.getColor(temperature)
}

private fun satellite(planet: Planet, x: Int, y: Int): RGB {
    val altitude = planet.regions[x][y].temperature
    return satelliteSpectrum.getColor(altitude)
}

private fun biome(planet: Planet, x: Int, y: Int): RGB {
    return planet.regions[x][y].biome.rgb
}