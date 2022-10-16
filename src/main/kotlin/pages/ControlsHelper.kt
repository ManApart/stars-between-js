package pages

import el
import kotlinx.html.*
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTableCellElement
import pages.planetGen.PlanetViewType
import kotlin.reflect.KMutableProperty0

fun TagConsumer<HTMLElement>.checkBoxDiv(label: String, backingProperty: KMutableProperty0<Boolean>, onUpdate: () -> Unit = {}) {
    val idString = backingProperty.name + "-checkbox"
    div {
        checkBoxInput {
            id = idString
            checked = backingProperty.get()
            onClickFunction = {
                with(el<HTMLInputElement>(idString)) {
                    backingProperty.set(checked)
                    onUpdate()
                }
            }
        }
        label {
            +label
            onClickFunction = {
                with(el<HTMLInputElement>(idString)) {
                    checked = !checked
                    backingProperty.set(checked)
                    onUpdate()
                }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.rangeTableRow(label: String, backingProperty: KMutableProperty0<Int>, min: Int = 0, max: Int = 100, step: Int = 1, onUpdate: () -> Unit = {}) =
    rangeTableRow(label, backingProperty, { it.toInt() }, min, max, step, onUpdate)

fun TagConsumer<HTMLElement>.rangeTableRow(label: String, backingProperty: KMutableProperty0<Long>, min: Int = 0, max: Int = 100, step: Int = 1, onUpdate: () -> Unit = {}) =
    rangeTableRow(label, backingProperty, { it.toLong() }, min, max, step, onUpdate)

fun TagConsumer<HTMLElement>.rangeTableRow(label: String, backingProperty: KMutableProperty0<Float>, min: Int = 0, max: Int = 100, step: Int = 1, onUpdate: () -> Unit = {}) =
    rangeTableRow(label, backingProperty, { it.toFloat() }, min, max, step, onUpdate)

fun TagConsumer<HTMLElement>.rangeTableRow(label: String, backingProperty: KMutableProperty0<Double>, min: Int = 0, max: Int = 100, step: Int = 1, onUpdate: () -> Unit = {}) =
    rangeTableRow(label, backingProperty, { it.toDouble() }, min, max, step, onUpdate)


private fun <T> TagConsumer<HTMLElement>.rangeTableRow(label: String, backingProperty: KMutableProperty0<T>, backingPropertyUpdater: (String) -> T, min: Int, max: Int, step: Int, onUpdate: () -> Unit = {}) {
    tr {
        td { +label }
        td {
            label {
                id = "${backingProperty.name}-range-cell"
                +"${backingProperty.get()}"
            }
            rangeInput {
                id = "${backingProperty.name}-range"
                this.min = "$min"
                this.max = "$max"
                this.step = "$step"
                this.value = "${backingProperty.get()}"
                placeholder = "${backingProperty.get()}"
                onChangeFunction = {
                    val newValue = el<HTMLInputElement>("${backingProperty.name}-range").value
                    el<HTMLTableCellElement>("${backingProperty.name}-range-cell").innerText = newValue
                    backingProperty.set(backingPropertyUpdater(newValue))
                    onUpdate()
                }
            }
        }
    }
}

fun <T : Enum<*>> TagConsumer<HTMLElement>.dropDown(backingProperty: KMutableProperty0<T>, values: List<T>, onUpdate: () -> Unit = {}) {
    div {
        select {
            id = "${backingProperty.name}-select"
            values.forEach { type ->
                option {
                    value = type.name
                    +type.name.lowercase().split("_").joinToString(" ") { it.capitalize() }
                    selected = type == backingProperty.get()
                }
                onChangeFunction = {
                    backingProperty.set(values[el<HTMLSelectElement>("${backingProperty.name}-select").selectedIndex])
                    onUpdate()
                }
            }
        }
    }
}