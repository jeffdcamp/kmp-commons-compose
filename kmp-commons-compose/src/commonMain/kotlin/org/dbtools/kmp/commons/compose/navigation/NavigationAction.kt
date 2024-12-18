@file:Suppress("unused")

package org.dbtools.kmp.commons.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed interface NavigationActionRoute : NavigationAction {
    /**
     * @return true if navController.popBackStack() called AND was successful
     */
    fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean
}

sealed interface NavigationAction {
    data class Navigate(private val route: NavigationRoute) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            navController.navigate(route)

            resetNavigate(this)
            return false
        }
    }

    data class NavigateMultiple(val routes: List<NavigationRoute>) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            routes.forEach { route ->
                navController.navigate(route)
            }

            resetNavigate(this)
            return false
        }
    }

    data class NavigateWithOptions(val route: NavigationRoute, val navOptions: NavOptions) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            navController.navigate(route, navOptions)

            resetNavigate(this)
            return false
        }
    }

    data class PopAndNavigate(private val route: NavigationRoute) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            val stackPopped = navController.popBackStack()
            navController.navigate(route)

            resetNavigate(this)
            return stackPopped
        }
    }

    data class Pop(private val route: NavigationRoute? = null, private val inclusive: Boolean = false) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            val stackPopped = if (route == null) {
                navController.popBackStack()
            } else {
                navController.popBackStack(route, inclusive = inclusive)
            }

            resetNavigate(this)
            return stackPopped
        }
    }

    data class PopWithResult(
        private val resultValues: List<PopResultKeyValue>,
        private val route: NavigationRoute? = null,
        private val inclusive: Boolean = false,
    ) : NavigationActionRoute {
        override fun navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit): Boolean {
            val destinationNavBackStackEntry = if (route != null) {
                navController.getBackStackEntry(route)
            } else {
                navController.previousBackStackEntry
            }
            resultValues.forEach { destinationNavBackStackEntry?.savedStateHandle?.set(it.key, it.value) }
            val stackPopped = if (route == null) {
                navController.popBackStack()
            } else {
                navController.popBackStack(route, inclusive = inclusive)
            }

            resetNavigate(this)
            return stackPopped
        }
    }
}

data class PopResultKeyValue(val key: String, val value: Any)

fun NavigationAction.navigate(navController: NavController, resetNavigate: (NavigationAction) -> Unit) {
    when(this) {
        is NavigationActionRoute -> navigate(navController, resetNavigate)
    }
}
