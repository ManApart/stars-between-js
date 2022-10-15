package pages.planetGen

import clamp
import kotlin.math.roundToInt

class ColorSpectrum(private val keyFrames: Map<Int, RGB>) {
    private val positions = keyFrames.keys.sorted()

    fun getColor(position: Int): RGB {
        val before = getPrevious(position)
        val after = getNext(position)

        return when {
            before == after -> keyFrames[before]!! //If we're already out of range, return the last on the list
            position == before -> keyFrames[before]!!
            position == after -> keyFrames[after]!!
            else -> {
                val blendPercent = getBlendRelativeToLowerBound(before, after, position)
                blendRGB(keyFrames[before]!!, keyFrames[after]!!, blendPercent)
            }
        }


    }

    fun getNext(position: Int): Int {
        return positions.firstOrNull { it >= position } ?: positions.last()
    }

    fun getPrevious(position: Int): Int {
        return positions.sortedDescending().firstOrNull { it <= position } ?: positions.first()
    }

    fun getBlendRelativeToLowerBound(lowerBound: Int, upperBound: Int, position: Int): Float {
        val distanceBetweenBounds = (upperBound - lowerBound).toFloat()
        var progress = position - lowerBound
        progress = clamp(progress.toFloat(), 0f, distanceBetweenBounds).toInt()

        val percentRelativeToUpperBound = progress / distanceBetweenBounds
//		System.out.println("B: " + before.getPosition() + ", P: " + position + ", A: " + after.getPosition() + ", P: " + percent);
        return 1 - percentRelativeToUpperBound
    }

    private fun blendRGB(color: RGB, color2: RGB, desiredOpacity: Float): RGB {
        val opacity = clamp(desiredOpacity, 0f, 1f)
        val opacity2 = 1 - opacity

        val r = color.r * opacity + color2.r * opacity2
        val g = color.g * opacity + color2.g * opacity2
        val b = color.b * opacity + color2.b * opacity2

        return createValidRGB(r, g, b)
    }

    private fun createValidRGB(r: Float, g: Float, b: Float): RGB {
        val validR = clamp(r.roundToInt(), 0, 255)
        val validG = clamp(g.roundToInt(), 0, 255)
        val validB = clamp(b.roundToInt(), 0, 255)
        return RGB(validR, validG, validB)
    }

}

val altitudeSpectrum = ColorSpectrum(
    mapOf(
        -120 to RGB(0f, 0f, 0f),
        120 to RGB(1f, 1f, 1f)
    )
)

val precipitationSpectrum = ColorSpectrum(
    mapOf(
        0 to RGB(255, 255, 250),
        20 to RGB(1f, 1f, .6f),
        50 to RGB(.2f, .8f, 0f),
        100 to RGB(.1f, 0f, .5f)
    )
)

val temperatureSpectrum = ColorSpectrum(
    mapOf(
        -100 to RGB(0f, 0f, 0f),
        0 to RGB(0, 0, 255),
        0 to RGB(0, 0, 255),
        20 to RGB(54, 120, 204),
        50 to RGB(105, 255, 14),
        100 to RGB(236, 230, 168),
        200 to RGB(231, 37, 1),
        500 to RGB(255, 255, 219)
    )
)

val satelliteSpectrum = ColorSpectrum(
    mapOf(
        -120 to RGB(0.0f, 0.19607843458652496f, 0.5882353186607361f),
        0 to RGB(0.0f, 0.3921568691730499f, 0.7843137383460999f),
        5 to RGB(0.0784313753247261f, 0.3921568691730499f, 0.19607843458652496f),
        10 to RGB(0.0784313753247261f, 0.3921568691730499f, 0f),
        20 to RGB(0.19607843458652496f, 0.5882353186607361f, 0f),
        60 to RGB(0.19607843458652496f, 0.7843137383460999f, 0f),
        80 to RGB(.3f, .3f, .3f),
        120 to RGB(1f, 1f, 1f)
    )
)
