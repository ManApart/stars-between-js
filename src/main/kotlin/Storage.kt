import floorplan.Ship
import game.Game
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import persistence.PersistedCrewMan
import persistence.PersistedFloorPlan
import kotlin.js.Promise

@Serializable
data class PersistedShip(
    val floorPlan: PersistedFloorPlan,
    val crew: MutableMap<Int, PersistedCrewMan>
) {
    fun toShip(): Ship {
        val plan = floorPlan.toFloorPlan()
        return Ship(plan, crew.mapValues { (id, man) -> man.toCrewMan(id, plan) }.toMutableMap())
    }
}

fun Ship.persisted(): PersistedShip {
    return PersistedShip(PersistedFloorPlan(floorPlan), crew.mapValues { (id, man) -> PersistedCrewMan(man) }.toMutableMap())
}

val jsonMapper = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }

fun createDB() {
}

fun persistMemory() {
    LocalForage.setItem("memory", jsonMapper.encodeToString(Game.ship.persisted()))
}

fun loadMemory(): Promise<*> {
    return LocalForage.getItem("memory").then { persisted ->
        if (persisted != null && persisted != undefined) {
            Game.ship = jsonMapper.decodeFromString<PersistedShip>(persisted as String).toShip()
        }
    }
}