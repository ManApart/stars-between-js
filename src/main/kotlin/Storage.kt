import kotlinx.browser.localStorage
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.get
import org.w3c.dom.set
import org.w3c.files.Blob
import org.w3c.files.FileReader
import kotlin.js.Promise
import kotlin.js.Promise.Companion.resolve

@Serializable
data class InMemoryStorage(
val id: String = "stuff"
)

private var inMemoryStorage = InMemoryStorage()
val jsonMapper = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }

fun createDB() {
}

fun persistMemory() {
    LocalForage.setItem("memory", jsonMapper.encodeToString(inMemoryStorage))
}

fun loadMemory(): Promise<*> {
    return LocalForage.getItem("memory").then { persisted ->
        if (persisted != null && persisted != undefined) {
            inMemoryStorage = jsonMapper.decodeFromString(persisted as String)
        }
    }
}