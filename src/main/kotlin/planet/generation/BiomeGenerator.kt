package planet.generation

import jsonMapper
import kotlinx.serialization.decodeFromString
import planet.Biome
import planet.BiomeType

class BiomeGenerator(biomeData: Map<String, String>) {
    private var biomes = parseBiomes(biomeData)

    private fun parseBiomes(biomeFiles: Map<String, String>): Map<String, List<Biome>> {
        return biomeFiles.entries.associate { (fileName, fileContents) ->
            fileName to jsonMapper.decodeFromString(fileContents)
        }
    }

    fun getBiomes(type: BiomeType): List<Biome> {
        return biomes[type.fileName] ?: listOf()
    }

}
