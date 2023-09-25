package se.umu.svke0008.havencompanion.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import se.umu.svke0008.havencompanion.ui.theme.personAdd
import se.umu.svke0008.havencompanion.ui.theme.rememberPersonAdd

sealed interface Screen {
    val route: String
    val isTopAppBarVisible: Boolean
    val isBottomAppBarVisible: Boolean
    val navigationIcon: ImageVector?
    val navigationIconContentDescription: String?
    val onTopNavigationIconClick: (() -> Unit)?
    val onBottomNavigationIconClick: (() -> Unit)?
    val title: String
    val topActions: List<ActionMenuItem>
    val bottomActions: List<ActionMenuItem>
    val floatingActionButton: FloatingActionButton?
    val onFabClick: (() -> Unit)?
}

fun getScreen(route: String?): Screen? = Screen::class.nestedClasses.map {
        kClass -> kClass.objectInstance as Screen
}.firstOrNull { screen -> screen.route == route }

sealed interface FloatingActionButton {
    val text: String?
    val icon: ImageVector?
    val contentDescription: String?
    val onClick: (() -> Unit)

    data class FAB(
        override val text: String? = null,
        override val icon: ImageVector? = null,
        override val contentDescription: String? = null,
        override val onClick: (() -> Unit) = {/* TODO */}
    ): FloatingActionButton
}

sealed interface ActionMenuItem {
    val icon: ImageVector
    val contentDescription: String
    val onClick: (() -> Unit)?

    data class ActionItem(
        override val icon: ImageVector,
        override val contentDescription: String,
        override val onClick: (() -> Unit)?
    ) : ActionMenuItem
}


object InitiativeScreen: Screen {
    override val route: String = "initiative_screen"

    override val isTopAppBarVisible: Boolean = true
    override val isBottomAppBarVisible: Boolean = true
    override val navigationIcon: ImageVector = Icons.Filled.Menu
    override val navigationIconContentDescription: String = "Menu"

    override val onTopNavigationIconClick: (() -> Unit)? = null

    override val onBottomNavigationIconClick: (() -> Unit)? = null

    override val title: String = "Initiative screen"

    override val topActions: List<ActionMenuItem> = listOf(
        ActionMenuItem.ActionItem(
            icon = personAdd(),
            contentDescription = "Add character to scenario",
            onClick = onTopNavigationIconClick
        ),
        ActionMenuItem.ActionItem(
            icon = Icons.Default.Build,
            contentDescription = "Actiovate microphone",
            onClick = onTopNavigationIconClick
        )
    )

    override val bottomActions: List<ActionMenuItem> = listOf()
    override val onFabClick: () -> Unit = {}
    override val floatingActionButton: FloatingActionButton = FloatingActionButton.FAB(
        text = "New round",
        onClick = onFabClick
    )


}