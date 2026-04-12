package uk.vaent.kanaflash.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.reflect.KProperty1

/**
 * Radio buttons representing a boolean property of some data holder.
 * The `Yes` button represents a `true` value; `No` is equivalent to `false`.
 *
 * Callers must ensure that the effects of invoking [propertySetter] are reflected in the
 * [dataSource] argument, e.g. by having the setter function update an instance of MutableState
 * and passing the state value as the data source to trigger recomposition when it changes.
 *
 * @param T type of the data holder which declares the property.
 * @param dataSource the instance associated with these buttons.
 * @param dataProperty a boolean-valued property reference on the data type.
 * @param title human-readable description of the property, to display with the buttons.
 * @param propertySetter a function which accepts a boolean value and updates the dataSource.
 */
@Composable
fun <T> YesNoRadio(
    dataSource: T,
    dataProperty: KProperty1<T, Boolean>,
    title: String = "",
    propertySetter: (Boolean) -> Unit
) {
    Column {
        if (title.trim().isNotEmpty()) Text(title)
        Row {
            Text("Yes")
            DefaultRadioButton(dataProperty.get(dataSource)) { propertySetter(true) }
            Spacer(Modifier.width(15.dp))
            Text("No")
            DefaultRadioButton(!dataProperty.get(dataSource)) { propertySetter(false) }
        }
    }
}

@Composable
fun DefaultRadioButton(selected: Boolean, onClick: (() -> Unit)?) {
    RadioButton(
        selected,
        onClick,
        colors = colors(unselectedColor = MaterialTheme.colorScheme.secondary)
    )
}
