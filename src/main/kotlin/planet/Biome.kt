package planet

import kotlinx.serialization.Serializable
import kotlin.math.abs

val DEFAULT_BIOME = Biome("DefaultBiome", "Barren Rock", "#5d382d")

@Serializable
class Biome(
    val id: String,
    val name: String,
    val color: String,

    val altitude: Int = 0,
    val temperature: Int = 0,
    val precipitation: Int = 0,

    private val altitudeVariation: Int = 0,
    private val temperatureVariation: Int = 0,
    private val precipitationVariation: Int = 0,
) {

    override fun toString(): String {
        return "$name a:$altitude, t:$temperature, p:$precipitation"
    }

    fun couldContain(altitude: Int, temperature: Int, precipitation: Int): Boolean {
        return altitude in (altitude - altitudeVariation)..(altitude + altitudeVariation) &&
                temperature in (temperature - temperatureVariation)..(temperature + temperatureVariation) &&
                precipitation in (precipitation - precipitationVariation)..(precipitation + precipitationVariation)
    }

    fun getDeviation(altitude: Int, temperature: Int, precipitation: Int): Int {
        return abs(this.altitude - altitude) + abs(this.temperature - temperature) + abs(this.precipitation - precipitation)
    }


}
