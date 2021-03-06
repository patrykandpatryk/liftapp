@file:OptIn(ExperimentalMaterial3Api::class)

package com.patrykandpatryk.liftapp.feature.main.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.patrykandpatryk.liftapp.core.logging.CollectSnackbarMessages
import com.patrykandpatryk.liftapp.core.navigation.NavItemRoute
import com.patrykandpatryk.liftapp.core.navigation.Routes
import com.patrykandpatryk.liftapp.core.ui.anim.EXIT_ANIM_DURATION
import com.patrykandpatryk.liftapp.core.ui.anim.slideAndFadeIn
import com.patrykandpatryk.liftapp.feature.main.HomeViewModel
import com.patrykandpatryk.liftapp.feature.main.navigation.navBarRoutes

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Home(
    parentNavController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    CollectSnackbarMessages(messages = viewModel.messages, snackbarHostState = snackbarHostState)

    val navController = rememberAnimatedNavController()

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            NavigationBarWithPadding(
                navController = navController,
                navItemRoutes = navBarRoutes,
            )
        },
    ) { paddingValues ->

        val navBarRoutes = navBarRoutes

        AnimatedNavHost(
            route = Routes.Home.value,
            navController = navController,
            startDestination = navBarRoutes.first().route,
            enterTransition = { slideAndFadeIn() },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = EXIT_ANIM_DURATION))
            },
        ) {
            navBarRoutes.forEach { routeItem ->
                composable(
                    route = routeItem.route,
                ) { backStackEntry ->
                    routeItem.content(
                        entry = backStackEntry,
                        modifier = Modifier,
                        padding = paddingValues,
                        navigate = parentNavController::navigate,
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigationBarWithPadding(
    navController: NavController,
    navItemRoutes: Collection<NavItemRoute>,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp,
        modifier = modifier,
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination by derivedStateOf { currentBackStackEntry?.destination }

        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier.navigationBarsPadding(),
        ) {
            navItemRoutes.forEach { menuRoute ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == menuRoute.route
                    } ?: false,
                    onClick = { navController.navigate(menuRoute.route) },
                    icon = {
                        Icon(
                            painter = painterResource(id = menuRoute.iconRes),
                            contentDescription = stringResource(id = menuRoute.titleRes),
                        )
                    },
                    label = {
                        Text(text = stringResource(id = menuRoute.titleRes))
                    },
                )
            }
        }
    }
}
