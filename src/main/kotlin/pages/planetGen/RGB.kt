package pages.planetGen

data class RGB(val r: Int, val g: Int, val b: Int) {
    constructor(r: Float, g: Float, b: Float) : this((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
}