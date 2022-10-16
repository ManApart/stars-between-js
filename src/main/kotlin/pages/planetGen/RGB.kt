package pages.planetGen

data class RGB(val r: Int = 0, val g: Int = 0, val b: Int = 0) {
    constructor(r: Float, g: Float, b: Float) : this((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
}

fun String.toRGB(): RGB {
    val r = substring(1, 3).toInt(16)
    val g = substring(3, 5).toInt(16)
    val b = substring(5, 7).toInt(16)
    return RGB(r, g, b)
}