package org.dbtools.kmp.commons.compose.ext

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

fun Context.requireActivity(): ComponentActivity = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.requireActivity()
    else -> error("No Activity Found")
}
