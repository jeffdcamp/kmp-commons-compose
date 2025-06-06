package org.dbtools.kmp.commons.compose.form

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SwitchField(
    label: String,
    checkedFlow: StateFlow<Boolean>,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val checked by checkedFlow.collectAsState()

    Box(modifier = modifier) {
        Text(
            text = label,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
