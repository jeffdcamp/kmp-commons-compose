package org.dbtools.kmp.commons.compose.ext

import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Simply ONLY change the ListItem containerColor to Color.Transparent
 *
 * Example:
 * ListItem(
 *     headlineContent = { "Hello" }
 *     colors = ListItemDefaults.transparentContainerColors()
 * )
 */
@Composable
fun ListItemDefaults.transparentContainerColors(
    headlineColor: Color? = null,
    leadingIconColor: Color? = null,
    overlineColor: Color? = null,
    supportingColor: Color? = null,
    trailingIconColor: Color? = null,
    disabledHeadlineColor: Color? = null,
    disabledLeadingIconColor: Color? = null,
    disabledTrailingIconColor: Color? = null,
): ListItemColors {
    val defaultColors = colors()

    return colors(
        containerColor = Color.Transparent,
        headlineColor = headlineColor ?: defaultColors.contentColor,
        leadingIconColor = leadingIconColor ?: defaultColors.leadingContentColor,
        overlineColor = overlineColor ?: defaultColors.overlineContentColor,
        supportingColor = supportingColor ?: defaultColors.supportingContentColor,
        trailingIconColor = trailingIconColor ?: defaultColors.trailingContentColor,
        disabledHeadlineColor = disabledHeadlineColor ?: defaultColors.disabledContentColor,
        disabledLeadingIconColor = disabledLeadingIconColor ?: defaultColors.disabledLeadingContentColor,
        disabledTrailingIconColor = disabledTrailingIconColor ?: defaultColors.disabledTrailingContentColor,
    )
}
