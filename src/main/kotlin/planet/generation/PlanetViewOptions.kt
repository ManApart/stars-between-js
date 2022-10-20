package planet.generation

import pages.planetGen.PlanetViewType

class PlanetViewOptions(
    var viewType: PlanetViewType = PlanetViewType.BIOME,
    var sphere: Boolean = false,
    var shadow: Boolean = true,
    var autoUpdate: Boolean = false
)