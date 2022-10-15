package pages.planetGen

import kotlinx.html.InputType
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.ImageData
import planet.Planet
import planet.generation.PlanetViewOptions

internal fun CanvasRenderingContext2D.drawPlanet(planet: Planet, options: PlanetViewOptions) {
    when (options.viewType) {
        PlanetViewType.ALTITUDE -> drawAltitude(planet)
        PlanetViewType.BIOME -> drawBiome(planet)
        PlanetViewType.PRECIPITATION -> drawPrecipitation(planet)
        PlanetViewType.TEMPERATURE -> drawTemperature(planet)
        PlanetViewType.SATELLITE -> drawSatellite(planet)
        else -> drawAltitude(planet)
    }
}

private fun CanvasRenderingContext2D.drawAltitude(planet: Planet) {
    val imageSize = planet.regions.size
    val imageData = createImageData(imageSize.toDouble(), imageSize.toDouble())
    for (x in planet.regions.indices) {
        for (y in planet.regions.indices) {
            val altitude = planet.regions[x][y].altitude
            val color = altitudeSpectrum.getColor(altitude)
            imageData.pixel(color, x, y, imageSize)
        }
    }

    putImageData(imageData, 0.0, 0.0)
}

private fun CanvasRenderingContext2D.drawPrecipitation(planet: Planet) {
    val imageSize = planet.regions.size
    val imageData = createImageData(imageSize.toDouble(), imageSize.toDouble())
    for (x in planet.regions.indices) {
        for (y in planet.regions.indices) {
            val precipitation = planet.regions[x][y].precipitation
            val color = precipitationSpectrum.getColor(precipitation)
            imageData.pixel(color, x, y, imageSize)
        }
    }

    putImageData(imageData, 0.0, 0.0)
}

private fun CanvasRenderingContext2D.drawTemperature(planet: Planet) {
    val imageSize = planet.regions.size
    val imageData = createImageData(imageSize.toDouble(), imageSize.toDouble())
    for (x in planet.regions.indices) {
        for (y in planet.regions.indices) {
            val temperature = planet.regions[x][y].temperature
            val color = temperatureSpectrum.getColor(temperature)
            imageData.pixel(color, x, y, imageSize)
        }
    }

    putImageData(imageData, 0.0, 0.0)
}

private fun CanvasRenderingContext2D.drawSatellite(planet: Planet) {
    val imageSize = planet.regions.size
    val imageData = createImageData(imageSize.toDouble(), imageSize.toDouble())
    for (x in planet.regions.indices) {
        for (y in planet.regions.indices) {
            val altitude = planet.regions[x][y].temperature
            val color = satelliteSpectrum.getColor(altitude)
            imageData.pixel(color, x, y, imageSize)
        }
    }

    putImageData(imageData, 0.0, 0.0)
}

private fun CanvasRenderingContext2D.drawBiome(planet: Planet) {
    val imageSize = planet.regions.size
    val imageData = createImageData(imageSize.toDouble(), imageSize.toDouble())
    for (x in planet.regions.indices) {
        for (y in planet.regions.indices) {
            val color = planet.regions[x][y].biome.rgb
            imageData.pixel(color, x, y, imageSize)
        }
    }

    putImageData(imageData, 0.0, 0.0)
}

private fun ImageData.pixel(color: RGB, x: Int, y: Int, imageSize: Int) {
    val i = 4 * (y * imageSize + x)
    pixelData(this, i, color.r)
    pixelData(this, i + 1, color.g)
    pixelData(this, i + 2, color.b)
    pixelData(this, i + 3, 255)
}

private fun pixelData(imageData: ImageData, key: Int, value: Int) {
    js("imageData.data[key]=value")
}