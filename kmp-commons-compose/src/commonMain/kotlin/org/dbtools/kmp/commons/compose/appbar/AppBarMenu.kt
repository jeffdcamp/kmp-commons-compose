package org.dbtools.kmp.commons.compose.appbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.dbtools.kmp.commons.compose.menu.OverflowMenuItem
import org.dbtools.kmp.commons.compose.menu.OverflowMenuItemsContent

@Composable
fun AppBarMenu(menuItems: List<AppBarMenuItem>) {
    val itemsOnBar = menuItems.filter { !it.isOverFlowItem() }
    val overflowMenuItems = menuItems.filter { it.isOverFlowItem() }

    // show items on bar first (in the order received)
    itemsOnBar.forEach {
        when (it) {
            is AppBarMenuItem.Icon -> AppBarIcon(it)
            is AppBarMenuItem.Text -> AppBarText(it)
            is AppBarMenuItem.TextButton -> AppBarTextButton(it)
            is AppBarMenuItem.Custom -> AppBarCustom(it)
            else -> error("Unexpected item (filter failed)")
        }
    }

    // Show overflow items
    AppBarOverflowMenu(overflowMenuItems)
}

@Composable
fun AppBarIcon(menuItem: AppBarMenuItem.Icon) {
    AppBarIcon(menuItem.imageVector, menuItem.text(), menuItem.action)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarIcon(imageVector: ImageVector, contentDescription: String, action: () -> Unit) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip {
                Text(contentDescription)
            }
        },
        state = rememberTooltipState()
    ) {
        IconButton(
            onClick = action,
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
            )
        }
    }
}

@Composable
fun AppBarText(text: String, colors: ButtonColors? = null, action: () -> Unit) {
    TextButton(
        onClick = action,
        colors = colors ?: ButtonDefaults.textButtonColors()
    ) {
        Text(text)
    }
}

@Composable
fun AppBarText(menuItem: AppBarMenuItem.Text) {
    AppBarText(text = menuItem.text(), colors = menuItem.colors, action = menuItem.action)
}

@Composable
fun AppBarTextButton(text: String, colors: ButtonColors? = null, action: () -> Unit) {
    Row {
        Button(
            onClick = action,
            colors = colors ?: ButtonDefaults.buttonColors(),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 8.dp,
                end = 12.dp,
                bottom = 8.dp
            )
        ) {
            Text(text)
        }

        Spacer(modifier = Modifier.width(4.dp))
    }
}

@Composable
fun AppBarTextButton(menuItem: AppBarMenuItem.TextButton) {
    AppBarTextButton(text = menuItem.text(), colors = menuItem.colors, action = menuItem.action)
}

@Composable
fun AppBarCustom(menuItem: AppBarMenuItem.Custom) {
    AppBarCustom(menuItem.content)
}

@Composable
fun AppBarCustom(content: @Composable () -> Unit) {
    content()
}

@Composable
fun AppBarOverflowMenu(menuItems: List<AppBarMenuItem>) {
    if (menuItems.isEmpty()) {
        return
    }

    val expanded = remember { mutableStateOf(false) }

    IconButton(onClick = {
        expanded.value = true
    }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null
        )
    }
    DropdownMenu(
        expanded = expanded.value,
        offset = DpOffset((-40).dp, (-40).dp),
        onDismissRequest = { expanded.value = false }) {

        // Convert AppBarMenuItem to OverflowMenuItem
        val overflowMenuItems: List<OverflowMenuItem> = menuItems
            .filter { it.isOverFlowItem() }
            .map {
                when (it) {
                    is OverflowMenuItem.MenuItem -> it
                    is OverflowMenuItem.Divider -> it
                    else -> error("Unsupported OverflowMenuItem type")
                }
            }

        OverflowMenuItemsContent(overflowMenuItems, expanded)
    }
}

sealed interface AppBarMenuItem {
    class Icon(val imageVector: ImageVector, val text: @Composable () -> String, val action: () -> Unit) : AppBarMenuItem

    /**
     * If setting colors, consider using ButtonDefaults.textButtonColors(contentColor = LocalContentColor.current)
     */
    class Text(val text: @Composable () -> String, val colors: ButtonColors? = null, val action: () -> Unit) : AppBarMenuItem

    /**
     * If setting colors, consider using ButtonDefaults.buttonColors(contentColor = LocalContentColor.current)
     */
    class TextButton(val text: @Composable () -> String, val colors: ButtonColors? = null, val action: () -> Unit) : AppBarMenuItem

    class Custom(val content: @Composable () -> Unit) : AppBarMenuItem

    class OverflowMenuItem(
        override val text: @Composable () -> String,
        override val leadingIcon: ImageVector? = null,
        override val trailingIcon: ImageVector? = null,
        override val action: () -> Unit
    ) : AppBarMenuItem, org.dbtools.kmp.commons.compose.menu.OverflowMenuItem.MenuItem(text, leadingIcon, trailingIcon, action)

    class OverflowMenuItemCustom(
        override val text: @Composable () -> String,
        override val leadingContent: (@Composable () -> Unit)? = null,
        override val trailingContent: (@Composable () -> Unit)? = null,
        override val action: () -> Unit
    ) : AppBarMenuItem, org.dbtools.kmp.commons.compose.menu.OverflowMenuItem.MenuItemCustom(text, leadingContent, trailingContent, action)

    object OverflowDivider : AppBarMenuItem, org.dbtools.kmp.commons.compose.menu.OverflowMenuItem.Divider()

    fun isOverFlowItem(): Boolean = this is OverflowMenuItem || this is OverflowDivider
}
