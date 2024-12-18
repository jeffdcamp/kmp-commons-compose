package org.dbtools.kmp.commons.compose.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalTime

@Composable
fun TimeClickableTextField(
    label: String,
    localTimeFlow: StateFlow<LocalTime?>,
    localTimeToString: (LocalTime?) -> String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorTextFlow: StateFlow<String?> = MutableStateFlow<String?>(null)
) {
    val time by localTimeFlow.collectAsStateWithLifecycle()
    val text = localTimeToString(time)
    val errorText by errorTextFlow.collectAsStateWithLifecycle()

    ClickableTextField(
        label = label,
        text = text,
        supportingText = errorText,
        isError = !errorText.isNullOrBlank(),
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun TimeClickableTextField(
    label: String,
    localTime: LocalTime,
    localTimeToString: (LocalTime?) -> String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null
) {
    val text = localTimeToString(localTime)

    ClickableTextField(
        label = label,
        text = text,
        supportingText = errorText,
        isError = !errorText.isNullOrBlank(),
        onClick = onClick,
        modifier = modifier
    )
}
