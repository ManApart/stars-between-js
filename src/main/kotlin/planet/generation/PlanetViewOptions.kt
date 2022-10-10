package planet.generation

import pages.planetGen.PlanetViewType

class PlanetViewOptions(
    var viewType: PlanetViewType = PlanetViewType.ALTITUDE,
    var sphere: Boolean = true,
    var shadow: Boolean = true
)