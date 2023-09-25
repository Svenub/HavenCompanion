package se.umu.svke0008.havencompanion.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import se.umu.svke0008.havencompanion.R
import se.umu.svke0008.havencompanion.ui.padding
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@Composable
fun DrawerSheet(
    currentRoute: String,
    onItemClick: (Screen) -> Unit
) {

    // Main content
    val mainScreens = listOf(
        Screen.InitiativeScreen,
        Screen.EnhancementScreen,

        )
    // Settings
    val otherScreens = listOf(
        Screen.SettingsSreen
    )


    // val selectedItem = remember { mutableStateOf(mainScreens[0]) }

    //  val navBackStackEntry by navController.currentBackStackEntryAsState()
    //  val currentDestination = navBackStackEntry?.destination?.route


    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        drawerContentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium),
                text = "Haven Companion",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = MaterialTheme.padding.medium),
                text = "Version " + stringResource(id = R.string.app_version),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Divider(color = MaterialTheme.colorScheme.onSecondaryContainer)
        }

        mainScreens.forEach { screen ->
            NavigationDrawerItem(
                shape = MaterialTheme.shapes.medium,
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,

                    ),
                modifier = Modifier.padding(MaterialTheme.padding.extraSmall),
                icon = {
                    screen.icon?.let { icon ->
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null
                        )
                    }
                },
                label = { Text(text = screen.title, style = MaterialTheme.typography.titleMedium) },
                selected = screen.route == currentRoute,
                onClick = {
                    onItemClick.invoke(screen)
                    // selectedItem.value = screen
                    // navController.navigate(screen.route)
                },
            )

        }

        Divider(color = MaterialTheme.colorScheme.onSecondaryContainer)
        Text(text = "", modifier = Modifier.padding(MaterialTheme.padding.default))
        otherScreens.forEach { screen ->
            NavigationDrawerItem(
                shape = MaterialTheme.shapes.medium,
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,

                    ),
                modifier = Modifier.padding(MaterialTheme.padding.extraSmall),
                icon = {
                    screen.icon?.let { icon ->
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null
                        )
                    }
                },
                label = { Text(text = screen.title, style = MaterialTheme.typography.titleMedium) },
                selected = screen.route == currentRoute,
                onClick = {
                    onItemClick(screen)
                },
            )

        }

    }
}

@Preview
@Composable
fun PrevDrawerSheet() {

    HavenCompanionTheme() {
        DrawerSheet(
            currentRoute = Screen.InitiativeScreen.route,
            onItemClick = {})

    }
}