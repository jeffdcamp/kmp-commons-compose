package org.dbtools.kmp.commons.compose.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import org.dbtools.kmp.commons.compose.LibraryTheme

@Composable
fun MessageDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String? = null,
    icon: @Composable (() -> Unit)? = null,
    confirmButtonText: String? = null,
    onConfirmButtonClick: (() -> Unit)? = null,
    dismissButtonText: String? = null,
    onDismissButtonClick: (() -> Unit)? = null,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation
) {
    require(title != null || text != null) { "Title or Text is required (if visible)" }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = if (title != null) {
            { Text(title, style = MaterialTheme.typography.headlineSmall) }
        } else {
            null
        },
        text = if (text != null) {
            { Text(text, style = MaterialTheme.typography.bodyMedium) }
        } else {
            null
        },
        icon = icon,
        confirmButton = {
            if (onConfirmButtonClick != null && confirmButtonText != null) {
                TextButton(
                    onClick = {
                        onConfirmButtonClick()
                    }
                ) {
                    Text(confirmButtonText)
                }
            }
        },
        dismissButton = {
            if (onDismissButtonClick != null && dismissButtonText != null) {
                TextButton(
                    onClick = {
                        onDismissButtonClick()
                    }
                ) {
                    Text(dismissButtonText)
                }
            }
        },
        tonalElevation = tonalElevation
    )
}

@Composable
fun MessageDialog(
    dialogUiState: MessageDialogUiState
) {
    MessageDialog(
        onDismissRequest = dialogUiState.onDismissRequest,
        title = dialogUiState.title?.invoke(),
        text = dialogUiState.text?.invoke(),
        icon = dialogUiState.icon,
        confirmButtonText = dialogUiState.confirmButtonText?.invoke(),
        onConfirmButtonClick = if (dialogUiState.onConfirm != null) { { dialogUiState.onConfirm.invoke(Unit) } } else null,
        dismissButtonText = dialogUiState.dismissButtonText?.invoke(),
        onDismissButtonClick = dialogUiState.onDismiss,
    )
}

data class MessageDialogUiState(
    val title: @Composable (() -> String)? = null,
    val text: @Composable (() -> String)? = null,
    val icon: @Composable (() -> Unit)? = null,
    val confirmButtonText: @Composable (() -> String)? = null,
    val dismissButtonText: @Composable (() -> String)? = null,
    override val onConfirm: ((Unit) -> Unit)? = {},
    override val onDismiss: (() -> Unit)? = null,
    override val onDismissRequest: () -> Unit = {},
) : DialogUiState<Unit>

@Suppress("UnusedPrivateMember")
//@PreviewLibraryDefault
@Composable
private fun PreviewMessageDialog() {
    LibraryTheme {
        MessageDialog(
            title = "Title",
            text = "This is the content that goes in the text",
            onDismissRequest = { },
            onConfirmButtonClick = { },
            onDismissButtonClick = { }
        )
    }
}

@Suppress("UnusedPrivateMember")
//@PreviewLibraryDefault
@Composable
private fun PreviewMessageDialogWithIcon() {
    LibraryTheme {
        MessageDialog(
            title = "Title",
            text = "This is the content that goes in the text",
            icon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null) },
            onDismissRequest = { },
            onConfirmButtonClick = { },
            onDismissButtonClick = { }
        )
    }
}
