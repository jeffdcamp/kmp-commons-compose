@file:Suppress("unused")

package org.dbtools.kmp.commons.compose.form

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.dbtools.kmp.commons.compose.component.DayNightTextField

@Composable
fun <T> DropdownMenuBoxField(
    options: List<T>,
    selectedOptionFlow: StateFlow<T?>,
    onOptionSelected: (T) -> Unit,
    optionToText: @Composable (T) -> String,
    modifier: Modifier = Modifier,
    label: String? = null,
    supportingText: String? = null,
    errorTextFlow: StateFlow<String?> = MutableStateFlow(null),
) {
    val selectedOption by selectedOptionFlow.collectAsState()
    val errorText by errorTextFlow.collectAsState()

    val supportText = when {
        !errorText.isNullOrBlank() -> errorText
        !supportingText.isNullOrBlank() -> supportingText
        else -> null
    }

    DropdownMenuBoxField(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = onOptionSelected,
        optionToText = optionToText,
        modifier = modifier,
        label = label,
        supportingText = supportText,
        isError = !errorText.isNullOrBlank()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenuBoxField(
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    optionToText: @Composable (T) -> String,
    modifier: Modifier = Modifier,
    label: String? = null,
    supportingText: String? = null,
    isError: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        DayNightTextField(
            readOnly = true,
            value = selectedOption?.let { optionToText(it) }.orEmpty(),
            onValueChange = {},
            label = if (label != null) { { Text(text = label) } } else null,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            supportingText = supportingText?.let{ { Text(it) } },
            isError = isError,
            modifier = modifier
                // As of Material3 1.0.0-beta03; The `menuAnchor` modifier must be passed to the text field for correctness.
                // (https://android-review.googlesource.com/c/platform/frameworks/support/+/2200861)
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(optionToText(option)) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
