@file:Suppress("unused")

package org.dbtools.kmp.commons.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface DialogUiState<T> {
    val onConfirm: ((T) -> Unit)?
    val onDismiss: (() -> Unit)?
    val onDismissRequest: (() -> Unit)?
}

@Composable
fun <T : DialogUiState<*>> HandleDialogUiState(
    dialogUiStateFlow: StateFlow<T?>,
    dialog: @Composable (T) -> Unit = { dialogUiState -> LibraryDialogs(dialogUiState) }
) {
    val dialogUiState by dialogUiStateFlow.collectAsState()

    dialogUiState?.let {
        dialog(it)
    }
}

@Composable
fun LibraryDialogs(dialogUiState: DialogUiState<*>) {
    when (dialogUiState) {
        is MessageDialogUiState -> MessageDialog(dialogUiState)
        is InputDialogUiState -> InputDialog(dialogUiState)
        is TwoInputDialogUiState -> TwoInputDialog(dialogUiState)
        is RadioDialogUiState -> RadioDialog(dialogUiState)
        is MenuOptionsDialogUiState -> MenuOptionsDialog(dialogUiState)
        is MultiSelectDialogUiState<*> -> MultiSelectDialog(dialogUiState)
        is DropDownMenuDialogUiState -> DropDownMenuDialog(dialogUiState)
        is ProgressIndicatorDialogUiState -> ProgressIndicatorDialog(dialogUiState)
        is DatePickerDialogUiState -> DatePickerDialog(dialogUiState)
        is DateRangePickerDialogUiState -> DateRangePickerDialog(dialogUiState)
        is TimePickerDialogUiState -> TimePickerDialog(dialogUiState)
    }
}

fun showMessageDialog(
    dialogUiStateFlow: MutableStateFlow<DialogUiState<*>?>,
    title: @Composable (() -> String)? = null,
    text: @Composable (() -> String)? = null,
    confirmButtonText: @Composable (() -> String)? = null,
    dismissButtonText: @Composable (() -> String)? = null,
    onConfirm: (() -> Unit)? = {},
    onDismiss: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = { dismissDialog(dialogUiStateFlow) }
) {
    dialogUiStateFlow.value = MessageDialogUiState(
        title = title,
        text = text,
        confirmButtonText = confirmButtonText,
        dismissButtonText = dismissButtonText,
        onConfirm = if (onConfirm != null) {
            {
                onConfirm()
                dismissDialog(dialogUiStateFlow)
            }
        } else {
            null
        },
        onDismiss = if (onDismiss != null) {
            {
                onDismiss()
                dismissDialog(dialogUiStateFlow)
            }
        } else {
            null
        },
        onDismissRequest = {
            onDismissRequest?.invoke()
        }
    )
}

fun dismissDialog(
    dialogUiStateFlow: MutableStateFlow<DialogUiState<*>?>
) {
    dialogUiStateFlow.value = null
}
