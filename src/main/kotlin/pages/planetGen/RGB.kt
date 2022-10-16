package pages.planetGen

data class RGB(val r: Int = 0, val g: Int = 0, val b: Int = 0) {
    constructor(r: Float, g: Float, b: Float) : this((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
    constructor(hexCode: String) : this(hexCode.substring(1, 3).toInt(16), hexCode.substring(3, 5).toInt(16), hexCode.substring(5, 7).toInt(16))
}