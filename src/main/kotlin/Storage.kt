import floorplan.Ship
import game.Game
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import persistence.*
import kotlin.js.Promise

@Serializable
data class PersistedShip(
    val floorPlan: PersistedFloorPlan,
    val crew: Map<Int, PersistedCrewMan>
) {
    fun toShip(): Ship {
        val plan = floorPlan.toFloorPlan()
        return Ship(plan, crew.mapValues { (id, man) -> man.toCrewMan(id, plan) }.toMutableMap())
    }
}

fun Ship.persisted(): PersistedShip {
    return PersistedShip(PersistedFloorPlan(floorPlan), crew.mapValues { (id, man) -> PersistedCrewMan(man) })
}

val jsonMapper = Json {
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {
        polymorphic(PersistedSystem::class) {
            subclass(PersistedEngine::class, PersistedEngine.serializer())
            subclass(PersistedFloor::class, PersistedFloor.serializer())
            subclass(PersistedShield::class, PersistedShield.serializer())
            subclass(PersistedSpace::class, PersistedSpace.serializer())
            subclass(PersistedVent::class, PersistedVent.serializer())
            subclass(PersistedWall::class, PersistedWall.serializer())
            subclass(PersistedWire::class, PersistedWire.serializer())
        }
    }
}

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