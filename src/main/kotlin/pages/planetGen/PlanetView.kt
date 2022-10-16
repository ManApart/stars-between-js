package pages.planetGen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.ImageData
import planet.Planet
import planet.generation.PlanetViewOptions

internal fun CanvasRenderingContext2D.drawPlanet(planet: Planet, options: PlanetViewOptions) {
    val imageSize = planet.regions.size
    val imageSizeD = planet.regions.size.toDouble()
    val imageData = createImageData(imageSizeD, imageSizeD)
    for (x in planet.regions.indices) {
        for (y in planet.regions.indices) {
            val color = options.viewType.pixelColor(planet, x, y)
            imageData.pixel(color, x, y, imageSize)
        }
    }
    putImageData(imageData, 0.0, 0.0)

//    drawShadow(options)
}

private fun CanvasRenderingContext2D.drawShadow(options: PlanetViewOptions) {
    if (options.shadow) {
        println("ap[ply shadow")
//        val offset = imageSizeD / 10
//        lineWidth = offset
//        strokeStyle = "rgba(0,0,0,.5)"
//        val pi = js("Math.PI") as Double
        fillRect(0.0, 0.0, 10.0, 10.0)
//        beginPath()
//        arc(imageSizeD / 2 - offset, imageSizeD / 2 - offset, imageSizeD/2 , 0.0, 2 * pi)
//        stroke()
//        beginPath()
//        arc(imageSizeD / 2, imageSizeD / 2, imageSizeD / 2, 0.0, 2 * pi)
//        stroke()

    }
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