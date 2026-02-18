package org.dbtools.kmp.commons.compose.form

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.dbtools.kmp.commons.compose.component.DayNightPasswordTextField
import org.dbtools.kmp.commons.compose.util.formKeyEventHandler

@Composable
fun PasswordFlowTextField(
    label: String,
    textFlow: StateFlow<String>,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorTextFlow: StateFlow<String?> = MutableStateFlow(null),
) {
    val text by textFlow.collectAsState()
    val errorText by errorTextFlow.collectAsState()

    PasswordFlowTextField(
        label = label,
        text = text,
        onChange = onChange,
        modifier = modifier,
        errorText = errorText
    )
}

@Composable
fun PasswordFlowTextField(
    label: String,
    text: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null,
) {
    val focusManager = LocalFocusManager.current

    DayNightPasswordTextField(
        value = text,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        isError = !errorText.isNullOrBlank(),
        supportingText = errorText?.let{ { Text(it) } },
        modifier = modifier
            .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
    )
}
