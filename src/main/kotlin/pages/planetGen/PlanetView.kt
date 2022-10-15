package pages.planetGen

import org.khronos.webgl.Uint8ClampedArray
import org.khronos.webgl.set
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.ImageData
import planet.Planet
import planet.generation.PlanetViewOptions

internal fun CanvasRenderingContext2D.drawPlanet(planet: Planet, options: PlanetViewOptions) {
    when(options.viewType){
        PlanetViewType.ALTITUDE -> drawAltitude(planet)
        else -> drawAltitude(planet)
    }
}

private fun CanvasRenderingContext2D.drawAltitude(planet: Planet) {
    val imageSize = planet.regions.size
//    val imageData = createImageData(imageSize.toDouble(), imageSize.toDouble())
//    for (x in planet.regions.indices) {
//        for (y in planet.regions.indices) {
//            val altitude = planet.regions[x][y].altitude
////            val color = altitudeSpectrum.getColor(altitude)
//            val color = ""
//            imageData.pixel(color, x, y, imageSize)
//        }
//    }
        fillStyle = "rgba(255,255,255,1)"

        fillRect(0.0, 0.0, imageSize.toDouble(), imageSize.toDouble())

//    for (i in 0 until (imageData.data.length / 4)) {  //iterate over every pixel in the canvas
////        imageData.data.set(arrayOf(
////            255.toByte(),
////            0.toByte(),
////            0.toByte(),
////            100.toByte(),
////        ), 4*i)
//        imageData.data[4 * i] = 255.toByte() // RED (0-255)
//        imageData.data[4 * i + 1] = 0.toByte() // GREEN (0-255)
//        imageData.data[4 * i + 2] = 0.toByte() // BLUE (0-255)
//        imageData.data[4 * i + 3] = 100.toByte() // APLHA (0-255)
//    }

//    putImageData(imageData, 0.0, 0.0)
    println("Drew Altitude")
}

private fun ImageData.pixel(color: String, x: Int, y: Int, imageSize: Int) {
    val i = y * imageSize + x
    data[4 * i] = 255.toByte()    // RED (0-255)
    data[4 * i + 1] = 255.toByte()    // GREEN (0-255)
    data[4 * i + 2] = 255.toByte()    // BLUE (0-255)
    data[4 * i + 3] = 100.toByte()  // APLHA (0-255)
}