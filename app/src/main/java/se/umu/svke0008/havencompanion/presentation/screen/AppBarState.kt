package se.umu.svke0008.havencompanion.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

class AppBarState(
    private val navController: NavController,
) {

    private val currentScreenRoute: String?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination?.route


    val currentScreen: Screen?
        @Composable get() = getScreen(currentScreenRoute)


    val isTopBarVisible: Boolean
        @Composable get() = currentScreen?.isTopAppBarVisible == true

    val isBottomBarVisible: Boolean
        @Composable get() = currentScreen?.isTopAppBarVisible == true

    val navigationIcon: ImageVector?
        @Composable get() = currentScreen?.navigationIcon

    val navigationIconContentDescription: String?
        @Composable get() = currentScreen?.navigationIconContentDescription

    val onTopBarNavigationIconClick: (() -> Unit)?
        @Composable get() = currentScreen?.onTopNavigationIconClick

    val onBottomBarNavigationIconClick: (() -> Unit)?
        @Composable get() = currentScreen?.onBottomNavigationIconClick

    val title: String
        @Composable get() = currentScreen?.title.orEmpty()

    val topBarActions: List<ActionMenuItem>
        @Composable get() = currentScreen?.topActions.orEmpty()

    val bottomBarActions: List<ActionMenuItem>
        @Composable get() = currentScreen?.bottomActions.orEmpty()
}

@Composable
fun rememberAppBarState(
    navController: NavController,
) = remember { AppBarState(navController) }