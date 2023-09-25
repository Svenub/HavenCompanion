package se.umu.svke0008.havencompanion.data.local.source

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.HeroNames
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.MonsterNames
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.ui.theme.BANNER_SPEAR_COLOR
import se.umu.svke0008.havencompanion.ui.theme.BLINK_BLADE_COLOR
import se.umu.svke0008.havencompanion.ui.theme.CRASHING_TIDE_COLOR
import se.umu.svke0008.havencompanion.ui.theme.DEATH_WALKER_COLOR
import se.umu.svke0008.havencompanion.ui.theme.DRIFTER_COLOR
import se.umu.svke0008.havencompanion.ui.theme.GEMINATE_COLOR
import se.umu.svke0008.havencompanion.ui.theme.MONSTER_COLOR_RED
import se.umu.svke0008.havencompanion.ui.theme.NECROMANCER_COLOR
import se.umu.svke0008.havencompanion.ui.theme.SNOWDANCER_COLOR

/**
 * This object serves as a data source for game characters in Frosthaven.
 * It provides utility functions to generate and retrieve both monster and hero characters,
 * along with their associated aliases and colors.
 */
object GameCharacterSource {

    /**
     * A list of all monsters, each associated with a specific color.
     */

    val monsters = MonsterNames.values().map { createMonster(it, MONSTER_COLOR_RED) }

    /**
     * A list of all heroes, each associated with a specific color.
     */
    val heroes = HeroNames.values().map { createHero(it, getHeroColor(it)) }


    /**
     * Generates a list of aliases for a given monster based on common mispronunciations or alternative phrasings.
     * @param monster The name of the monster for which aliases are to be generated.
     * @return A list of aliases for the given monster.
     */
    private fun generateMonsterAliases(monster: MonsterNames): List<String> {
        return when (monster) {
            MonsterNames.Algox_Guard -> listOf(
                "All gox guard",
                "Al gox guard",
                "Algox gourd, all gods guard"
            )

            MonsterNames.Algox_Archer -> listOf(
                "All gox archer",
                "Algox arch er",
                "Algox archor, all gods archer"
            )

            MonsterNames.Algox_Shaman -> listOf(
                "All gox shaman",
                "Algox shaman",
                "Algox shamen, all gods shaman"
            )

            MonsterNames.Algox_Scout -> listOf("All gox scout", "Algox sc out", "all gods scout")
            MonsterNames.Algox_Icespeaker -> listOf(
                "All gox ice speaker",
                "Algox iced speaker",
                "all gods icespeaker"
            )

            MonsterNames.Algox_Snowspeaker -> listOf(
                "All gox snow speaker",
                "Algox snowed speaker",
                "all gods snow speaker"
            )

            MonsterNames.Polar_Bear -> listOf("Polar bare", "Pole ar bear")
            MonsterNames.Living_Doom -> listOf("Living dune", "Live in doom")
            MonsterNames.Frozen_Corpse -> listOf("Frozen corps", "Frozen corpse e")
            MonsterNames.Ice_Wraith -> listOf("Ice wrath", "Iced wraith")
            MonsterNames.Burrowing_Blade -> listOf("Borrowing blade", "Burrow in blade")
            MonsterNames.Snow_Imp -> listOf("Snow imp", "Snow limp")
            MonsterNames.Chaos_Demon -> listOf("Chaos daemon", "Chaos deem on")
            MonsterNames.Lurker_Mindsnipper -> listOf(
                "Lurker mind snipper",
                "Lurk her mind snipper"
            )

            MonsterNames.Lurker_Clawcrusher -> listOf(
                "Lurker claw crusher",
                "Lurk her claw crusher"
            )

            MonsterNames.Lurker_Wavethrower -> listOf(
                "Lurker wave thrower",
                "Lurk her wave thrower"
            )

            MonsterNames.Lightning_Eel -> listOf("Light ning eel", "Lite ning eel")
            MonsterNames.Piranha_Pig -> listOf("Piranha pig", "Piran ha pig")
            MonsterNames.Ruined_Machine -> listOf("Ruined machine", "Ruin ed machine")
            MonsterNames.Steel_Automaton -> listOf("Steel auto mat on", "Steal automaton")
            MonsterNames.Robotic_Boltshooter -> listOf(
                "Robotic bolt shooter",
                "Robotic bolt shoot her"
            )

            MonsterNames.Flaming_Bladespinner -> listOf(
                "Flaming blade spinner",
                "Flame in blade spinner"
            )

            MonsterNames.Shrike_Fiend -> listOf("Shrike fiend", "Strike fiend")
            MonsterNames.City_Guard -> listOf("City guard", "City gourd")
            MonsterNames.City_Archer -> listOf("City archer", "City arch her")
            MonsterNames.Living_Bones -> listOf("Living bones", "Living boons")
            MonsterNames.Living_Spirit -> listOf("Living spirit", "Living spree it")
            MonsterNames.Frost_Demon -> listOf("Frost daemon", "Frosted demon")
            MonsterNames.Flame_Demon -> listOf("Flame daemon", "Flamed demon")
            MonsterNames.Earth_Demon -> listOf("Earth daemon", "Ear th demon")
            MonsterNames.Wind_Demon -> listOf("Wind daemon", "Winned demon")
            MonsterNames.Sun_Demon -> listOf("Sun daemon", "Sunned demon")
            MonsterNames.Night_Demon -> listOf("Night daemon", "Nite demon")
            MonsterNames.Savvas_Lavaflow -> listOf("Savvas lava flow", "Sav vas lava flow")
            MonsterNames.Savvas_Icestorm -> listOf("Savvas ice storm", "Sav vas ice storm")
            MonsterNames.Rending_Drake -> listOf("Rending drake", "Rend in drake")
            MonsterNames.Spitting_Drake -> listOf("Spitting drake", "Spit ting drake")
            MonsterNames.Black_Imp -> listOf("Black imp", "Black limp")
            MonsterNames.Forest_Imp -> listOf("Forest imp", "For rest imp")
            MonsterNames.Hound -> listOf("Hound", "Houn'd")
            MonsterNames.Ooze -> listOf("Ooze", "Ouz")
            MonsterNames.Vermling_Scout -> listOf("Vermling scout", "Verm ling scout")
            MonsterNames.Vermling_Shaman -> listOf("Vermling shaman", "Verm ling shaman")
            MonsterNames.Lurker -> listOf("Lurker", "Lurk her")
            MonsterNames.Ancient_Artillery -> listOf("Ancient artillery", "Ancient art illery")
            MonsterNames.Abael_Scout -> listOf("abba el scout","A bell scout", "Ab ael scout")
            MonsterNames.Abael_Herder -> listOf("abba el herder","A bell herder", "Ab ael herder")

        }
    }

    /**
     * Generates a list of aliases for a given hero based on common mispronunciations or alternative phrasings.
     * @param hero The name of the hero for which aliases are to be generated.
     * @return A list of aliases for the given hero.
     */
    private fun generateHeroAliases(hero: HeroNames): List<String> {
        return when (hero) {
            HeroNames.Banner_Spear -> listOf(
                "Banner spear",
                "Banner's pear",
                "Banners peer",
                "bannerspeare",
                "bannery spear",
                "battery spear",
                "battery Spare",
                "banners Bear",
                "bannerspayer",
                "banner Spirit"
            )

            HeroNames.Drifter -> listOf("Drifter", "Dri fter", "Drift her", "priser")
            HeroNames.Bone_Shaper -> listOf(
                "Bone Shaker",
                "Bone Paper",
                "Bone Snapper",
                "Bone Chaper",
                "Boned Shaper",
                "Bone Sapper",
                "Boon Shaper",
                "Bounce a Pair"
            )

            HeroNames.Geminate -> listOf(
                "Geminate",
                "Gem inate",
                "Gem in ate",
                "genemate",
                "Jenny Knight",
                "genemite",
                "ten minutes",
                "ten minute",
                "terminate",
                "geminute"
            )

            HeroNames.Blink_Blade -> listOf("Blink blade", "Blink b'lade", "Blink laid", "blinke Blade", "linkeblade")
            HeroNames.Deathwalker -> listOf("Deathwalker", "Death walker", "Death walk her")
            HeroNames.Crashing_Tide -> listOf("")
            HeroNames.Snowdancer -> listOf("")
        }
    }

    /**
     * Creates a monster character with a specified name and color.
     * @param name The name of the monster.
     * @param color The color associated with the monster.
     * @return A monster character with the given name and color.
     */


    private fun createMonster(name: MonsterNames, color: Color): GameCharacter.Monster {
        val aliases = generateMonsterAliases(name)
        return GameCharacter.Monster(
            id = 0,
            characterName = name.name.replace("_", " "),
            nameAlias = aliases,
            colorInt = color.toArgb()
        )
    }




    /**
     * Creates a hero character with a specified name and color.
     * @param name The name of the hero.
     * @param color The color associated with the hero.
     * @return A hero character with the given name and color.
     */

    private fun createHero(name: HeroNames, color: Color): GameCharacter.Hero {
        val aliases = generateHeroAliases(name)

        return GameCharacter.Hero(
            id = 0,
            characterName = name.name.replace("_", " "),
            nameAlias = aliases,
            colorInt = color.toArgb()
        )
    }



    /**
     * Retrieves the color associated with a specific hero.
     * @param name The name of the hero.
     * @return The color associated with the given hero.
     */
    private fun getHeroColor(name: HeroNames): Color {
        return when (name) {
            HeroNames.Banner_Spear -> BANNER_SPEAR_COLOR
            HeroNames.Drifter -> DRIFTER_COLOR
            HeroNames.Bone_Shaper -> NECROMANCER_COLOR
            HeroNames.Geminate -> GEMINATE_COLOR
            HeroNames.Blink_Blade -> BLINK_BLADE_COLOR
            HeroNames.Deathwalker -> DEATH_WALKER_COLOR
            HeroNames.Crashing_Tide -> CRASHING_TIDE_COLOR
            HeroNames.Snowdancer -> SNOWDANCER_COLOR
        }
    }
}