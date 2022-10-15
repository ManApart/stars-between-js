package pages.planetGen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.ImageData
import planet.Planet
import planet.generation.PlanetViewOptions

internal fun CanvasRenderingContext2D.drawPlanet(planet: Planet, options: PlanetViewOptions) {
    when (options.viewType) {
        PlanetViewType.ALTITUDE -> drawAltitude(planet)
        else -> drawAltitude(planet)
    }
}

private fun CanvasRenderingContext2D.drawAltitude(planet: Planet) {
    val imageSize = planet.regions.size
    val imageData = createImageData(imageSize.toDouble(), imageSize.toDouble())
    for (x in planet.regions.indices) {
        for (y in planet.regions.indices) {
            val altitude = planet.regions[x][y].altitude
//            val color = altitudeSpectrum.getColor(altitude)
            val color = ""
            imageData.pixel(color, x, y, imageSize)
        }
    }

    putImageData(imageData, 0.0, 0.0)
}

private fun ImageData.pixel(color: String, x: Int, y: Int, imageSize: Int) {
    val i = 4 * (y * imageSize + x)
    pixelData(this, i, 255)
    pixelData(this, i + 1, 255)
    pixelData(this, i + 2, 255)
    pixelData(this, i + 3, 100)
}

private fun pixelData(imageData: ImageData, key: Int, value: Int) {
    js("imageData.data[key]=value")
}