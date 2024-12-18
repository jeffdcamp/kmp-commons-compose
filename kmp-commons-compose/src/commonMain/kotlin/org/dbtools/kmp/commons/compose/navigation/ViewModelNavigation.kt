package org.dbtools.kmp.commons.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Used for typical ViewModels that have navigation
 */
interface ViewModelNavigation {
    val navigationActionFlow: StateFlow<NavigationAction?>

    fun navigate(route: NavigationRoute, popBackStack: Boolean = false)
    fun navigate(routes: List<NavigationRoute>)
    fun navigate(route: NavigationRoute, navOptions: NavOptions)
    fun navigate(route: NavigationRoute, optionsBuilder: NavOptionsBuilder.() -> Unit)
    fun popBackStack(route: NavigationRoute? = null, inclusive: Boolean = false)
    fun popBackStackWithResult(resultValues: List<PopResultKeyValue>, route: NavigationRoute? = null, inclusive: Boolean = false)

    fun navigate(navigationAction: NavigationAction)
    fun resetNavigate(navigationAction: NavigationAction)
}

class ViewModelNavigationImpl : ViewModelNavigation {
    private val _navigatorFlow = MutableStateFlow<NavigationAction?>(null)
    override val navigationActionFlow: StateFlow<NavigationAction?> = _navigatorFlow.asStateFlow()

    override fun navigate(route: NavigationRoute, popBackStack: Boolean) {
        _navigatorFlow.compareAndSet(null, if (popBackStack) NavigationAction.PopAndNavigate(route) else NavigationAction.Navigate(route))
    }

    override fun navigate(routes: List<NavigationRoute>) {
        _navigatorFlow.compareAndSet(null, NavigationAction.NavigateMultiple(routes))
    }

    override fun navigate(route: NavigationRoute, navOptions: NavOptions) {
        _navigatorFlow.compareAndSet(null, NavigationAction.NavigateWithOptions(route, navOptions))
    }

    override fun navigate(route: NavigationRoute, optionsBuilder: NavOptionsBuilder.() -> Unit) {
        _navigatorFlow.compareAndSet(null, NavigationAction.NavigateWithOptions(route, navOptions(optionsBuilder)))
    }

    override fun popBackStack(route: NavigationRoute?, inclusive: Boolean) {
        _navigatorFlow.compareAndSet(null, NavigationAction.Pop(route, inclusive))
    }

    override fun popBackStackWithResult(resultValues: List<PopResultKeyValue>, route: NavigationRoute?, inclusive: Boolean) {
        _navigatorFlow.compareAndSet(null, NavigationAction.PopWithResult(resultValues, route, inclusive))
    }

    override fun navigate(navigationAction: NavigationAction) {
        _navigatorFlow.compareAndSet(null, navigationAction)
    }

    override fun resetNavigate(navigationAction: NavigationAction) {
        _navigatorFlow.compareAndSet(navigationAction, null)
    }
}

@Composable
fun HandleNavigation(
    viewModelNavigation: ViewModelNavigation,
    navController: NavController?
) {
    navController ?: return
    val navigationActionState = viewModelNavigation.navigationActionFlow.collectAsStateWithLifecycle()
    val navigationAction = navigationActionState.value

    LaunchedEffect(navigationAction) {
        navigationAction?.navigate(navController, viewModelNavigation::resetNavigate)
    }
}
