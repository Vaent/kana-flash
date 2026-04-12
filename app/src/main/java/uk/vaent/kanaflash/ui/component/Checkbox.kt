package uk.vaent.kanaflash.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

fun defaultLabelSupplier(property: KProperty<Any>): String {
    return property.name.replaceFirstChar(Char::titlecase)
}

/**
 * A collection of checkboxes which are thematically related in some way.
 * Each checkbox represents a boolean property of some data holder.
 *
 * Callers must ensure that the effects of invoking property setter functions are reflected in the
 * [dataSource] argument, e.g. by having the setter function update an instance of MutableState
 * and passing the state value as the data source to trigger recomposition when it changes.
 *
 * @param T type of the data holder which declares the properties.
 * @param dataSource the instance associated with these checkboxes.
 * @param title human-readable description of this collection.
 * @param dataProperties boolean-valued property references on the data type.
 * @param labelSupplier a function which accepts a property reference on the data type,
 *      and returns a label for that property. By default, each checkbox is labelled with
 *      the associated property name, capitalised.
 * @param propertySetterSupplier a function which accepts a property reference on the data type,
 *      and returns another function which updates that property on the dataSource.
 */
@Composable
fun <T> CheckboxGroup(
    dataSource: T,
    title: String,
    vararg dataProperties: KProperty1<T, Boolean>,
    labelSupplier: (KProperty1<T, Boolean>) -> String = ::defaultLabelSupplier,
    propertySetterSupplier: (KProperty1<T, Boolean>) -> (Boolean) -> Unit
) {
    Column(
        Modifier
            .border(Dp.Hairline, MaterialTheme.colorScheme.onSurface)
            .padding(20.dp)
    ) {
        if (title.trim().isNotEmpty()) Text(title)
        dataProperties.forEach { property ->
            CheckboxWithLabel(
                labelSupplier(property),
                property.get(dataSource),
                propertySetterSupplier(property)
            )
        }
    }
}

@Composable
fun CheckboxWithLabel(label: String, value: Boolean, valueSetter: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = value, onCheckedChange = valueSetter)
        if (label.trim().isNotEmpty()) {
            Spacer(Modifier.width(5.dp))
            Text(label)
        }
    }
}
