package planet.generation

import pages.planetGen.PlanetViewType

class PlanetViewOptions(
    var viewType: PlanetViewType = PlanetViewType.BIOME,
    var sphere: Boolean = true,
    var shadow: Boolean = false,
    var autoUpdate: Boolean = false
)