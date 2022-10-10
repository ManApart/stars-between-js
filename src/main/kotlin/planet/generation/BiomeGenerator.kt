package planet.generation

import jsonMapper
import kotlinx.browser.window
import kotlinx.serialization.decodeFromString
import planet.Biome
import planet.BiomeType

class BiomeGenerator {
    private var biomes = mapOf<String, List<Biome>>()

    init {
        window.window
            .fetch("./biomes/EarthlikeBiomes.json")
            .then { data -> parseBiomes(mapOf("EarthlikeBiomes.json" to data as String)) }
    }

    private fun parseBiomes(biomeFiles: Map<String, String>): Map<String, List<Biome>> {
        return biomeFiles.entries.associate { (fileName, fileContents) ->
            fileName.replace(".json", "") to jsonMapper.decodeFromString(fileContents)
        }
    }

    fun getBiomes(type: BiomeType): List<Biome> {
        return biomes[type.fileName] ?: listOf()
    }

}
