package org.dbtools.kmp.commons.compose.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

@Composable
fun DateClickableTextField(
    label: String,
    localDateFlow: StateFlow<LocalDate?>,
    localDateToText: (LocalDate?) -> String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorTextFlow: StateFlow<String?> = MutableStateFlow<String?>(null)
) {
    val date by localDateFlow.collectAsStateWithLifecycle()
    val text = localDateToText(date)
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
fun DateClickableTextField(
    label: String,
    date: LocalDate,
    localDateToText: (LocalDate?) -> String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null
) {
    val text = localDateToText(date)

    ClickableTextField(
        label = label,
        text = text,
        supportingText = errorText,
        isError = !errorText.isNullOrBlank(),
        onClick = onClick,
        modifier = modifier
    )
}
