package shipStructor

import systems.ShipSystem
import tile.SystemType
import persistence.PersistedSystem
import persistence.PersistedWall

class Wall(health: Int = 100) : ShipSystem("Wall", SystemType.WALL, health, true) {
    override fun persisted(): PersistedSystem {
        return PersistedWall(this)
    }
}
