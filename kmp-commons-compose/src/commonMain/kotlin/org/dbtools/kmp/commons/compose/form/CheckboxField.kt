package org.dbtools.kmp.commons.compose.form

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.dbtools.kmp.commons.compose.LibraryTheme

@Composable
fun CheckboxField(
    label: String,
    checkedFlow: StateFlow<Boolean>,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val checked by checkedFlow.collectAsState()

    Row(modifier = modifier) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Text(
            text = label,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Suppress("UnusedPrivateMember")
//@PreviewLibraryDefault
@Composable
private fun Preview() {
    LibraryTheme {
        Surface {
            CheckboxField("Testing for really long text that could wrap.  So hopefully all will look good", MutableStateFlow(false), {})
        }
    }
}