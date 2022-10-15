package pages.planetGen

data class RGB(val r: Int = 0, val g: Int = 0, val b: Int = 0) {
    constructor(r: Float, g: Float, b: Float) : this((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
}

fun String.toRGB(): RGB {
    val parts = match("/.{1,2}/g")
    return if (parts == null) RGB() else {
        val r = parts[0].toInt(16)
        val g = parts[1].toInt(16)
        val b = parts[2].toInt(16)
        RGB(r, g, b)
    }
}