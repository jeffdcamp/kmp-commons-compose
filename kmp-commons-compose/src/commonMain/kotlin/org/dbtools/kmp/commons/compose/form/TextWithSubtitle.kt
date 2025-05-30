package org.dbtools.kmp.commons.compose.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.dbtools.kmp.commons.compose.LibraryTheme

@Composable
fun TextWithSubtitle(
    text: String?,
    label: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    if (text.isNullOrBlank()) {
        return
    }
    Column {
        val labelVisible = label != null

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = text,
            style = textStyle,
            modifier = Modifier
                .padding(start = 16.dp, top = if (labelVisible) 0.dp else 8.dp)
        )
        if (labelVisible && label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Suppress("UnusedPrivateMember")
//@PreviewLibraryDefault
@Composable
private fun Preview() {
    LibraryTheme {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextWithTitle(text = "John", label = "First Name")
            TextWithTitle(text = "Adams", label = "Last Name")
            TextWithTitle(text = "123 Main Street")
            TextWithSubtitle(text = "888-555-1234", label = "Home")
            TextWithSubtitle(text = "888-555-0000", label = "Work")
            TextWithSubtitle(text = "888-111-2222")
//            ListItem(headlineContent = { Text("801-456-0987")}, supportingText = { Text("Home")})
//            ListItem(headlineContent = { Text("801-111-0987")}, supportingText = { Text("Home")})
        }
    }
}
