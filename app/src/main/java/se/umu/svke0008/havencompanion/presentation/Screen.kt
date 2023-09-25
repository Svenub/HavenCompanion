package se.umu.svke0008.havencompanion.presentation

import se.umu.svke0008.havencompanion.R

/**
 * Represents the different screens in the application.
 * Each screen is associated with a unique route, title, and an optional icon.
 *
 * @property route The unique route identifier for the screen.
 * @property title The display title for the screen.
 * @property icon An optional icon associated with the screen.
 */

sealed class Screen(
    val route: String,
    val title: String,
    val icon: Int? = null
) {


    object InitiativeScreen :
        Screen(
            route = "initiative_screen",
            title = "Initiative screen",
            icon =  R.drawable.baseline_format_list_numbered_rtl_24
        )

    object EnhancementScreen :
        Screen(
            route = "enhancement_screen",
            title = "Enhancement screen",
            icon = R.drawable.baseline_star_border_24
        )

    object CharacterScreen: Screen(route = "character_screen", title = "Character screen") {
        object AddCharacterScreen :
            Screen(
                route = "add_character_screen",
                title = "Add character"
            )

        object CreateCharacterScreen: Screen(
            route = "create_character_screen",
            title = "Create character"
        )
    }


    object SettingsSreen : Screen(
        route = "settings_screen",
        title = "Settings",
        icon = R.drawable.baseline_settings_24
    )


}
