package org.dbtools.kmp.commons.compose.icons.google.automirrored.rounded

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.AutoMirrored.Rounded.ArrowForwardIos: ImageVector
    get() {
        if (_ArrowForwardIos != null) {
            return _ArrowForwardIos!!
        }
        _ArrowForwardIos = ImageVector.Builder(
            name = "AutoMirrored.Rounded.ArrowForwardIos",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f,
            autoMirror = true,
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(579f, 480f)
                lineTo(285f, 186f)
                quadToRelative(-15f, -15f, -14.5f, -35.5f)
                reflectiveQuadTo(286f, 115f)
                quadToRelative(15f, -15f, 35.5f, -15f)
                reflectiveQuadToRelative(35.5f, 15f)
                lineToRelative(307f, 308f)
                quadToRelative(12f, 12f, 18f, 27f)
                reflectiveQuadToRelative(6f, 30f)
                quadToRelative(0f, 15f, -6f, 30f)
                reflectiveQuadToRelative(-18f, 27f)
                lineTo(356f, 845f)
                quadToRelative(-15f, 15f, -35f, 14.5f)
                reflectiveQuadTo(286f, 844f)
                quadToRelative(-15f, -15f, -15f, -35.5f)
                reflectiveQuadToRelative(15f, -35.5f)
                lineToRelative(293f, -293f)
                close()
            }
        }.build()

        return _ArrowForwardIos!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowForwardIos: ImageVector? = null
