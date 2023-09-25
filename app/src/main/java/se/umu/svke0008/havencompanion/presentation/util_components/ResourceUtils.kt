package se.umu.svke0008.havencompanion.presentation.util_components

import android.graphics.Color
import se.umu.svke0008.havencompanion.R
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementType
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.ui.theme.BANNER_SPEAR_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.BLINK_BLADE_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.BONE_SHAPER_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.CRASHING_TIDE_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.DEATH_WALKER_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.DEEPWRAITH_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.DRIFTER_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.FROZEN_FIST_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.GEMINATE_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.HIVE_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.INFUSER_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.METAL_MOSAIC_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.MONSTER_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.PAIN_CONDUIT_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.PYROCLAST_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.SHATTERSONG_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.SNOWDANCER_PALETTE
import se.umu.svke0008.havencompanion.ui.theme.TRAPPER_PALETTE

object ResourceUtils {

    fun getDrawableIdFromIconName(iconName: String): Int {
        return when (iconName) {
            "fh_banner_spear_bw_icon" -> R.drawable.fh_banner_spear_bw_icon
            "fh_drifter_bw_icon" -> R.drawable.fh_drifter_bw_icon
            "fh_boneshaper_bw_icon" -> R.drawable.fh_boneshaper_bw_icon
            "fh_geminate_bw_icon" -> R.drawable.fh_geminate_bw_icon
            "fh_blinkblade_bw_icon" -> R.drawable.fh_blinkblade_bw_icon
            "fh_deathwalker_bw_icon" -> R.drawable.fh_deathwalker_bw_icon
            "fh_pain_conduit_bw_icon" -> R.drawable.fh_pain_conduit_bw_icon
            "fh_crashing_tide_bw_icon" -> R.drawable.fh_crashing_tide_bw_icon
            "fh_snowdancer_bw_icon" -> R.drawable.fh_snowdancer_bw_icon
            "fh_deepwraith_bw_icon" -> R.drawable.fh_deepwraith_bw_icon
            "fh_frozen_fist_bw_icon" -> R.drawable.fh_frozen_fist_bw_icon
            "fh_hive_bw_icon" -> R.drawable.fh_hive_bw_icon
            "fh_infuser_bw_icon" -> R.drawable.fh_infuser_bw_icon
            "fh_shattersong_bw_icon" -> R.drawable.fh_shattersong_bw_icon
            "fh_pyroclast_bw_icon" -> R.drawable.fh_pyroclast_bw_icon
            "fh_trapper_bw_icon" -> R.drawable.fh_trapper_bw_icon
            "fh_metal_mosaic_bw_icon" -> R.drawable.fh_metal_mosaic_bw_icon
            else -> R.drawable.skull
        }
    }

    private fun List<String>.toColorList(): List<Int> {
        return this.map { Color.parseColor(it) }
    }

    fun getColorPalette(gameCharacter: GameCharacter): List<Int> {
        return if(gameCharacter is GameCharacter.Monster) {
            MONSTER_PALETTE.toColorList()
        } else {
            return when(gameCharacter.characterName) {
                "Drifter" -> DRIFTER_PALETTE.toColorList()
                "Geminate" -> GEMINATE_PALETTE.toColorList()
                "Blink Blade" -> BLINK_BLADE_PALETTE.toColorList()
                "Banner Spear" -> BANNER_SPEAR_PALETTE.toColorList()
                "Deathwalker" -> DEATH_WALKER_PALETTE.toColorList()
                "Bone Shaper" -> BONE_SHAPER_PALETTE.toColorList()
                "Frozen Fist" -> FROZEN_FIST_PALETTE.toColorList()
                "Pain Conduit" -> PAIN_CONDUIT_PALETTE.toColorList()
                "Infuser" -> INFUSER_PALETTE.toColorList()
                "Snowdancer" -> SNOWDANCER_PALETTE.toColorList()
                "Shattersong" -> SHATTERSONG_PALETTE.toColorList()
                "Pyroclast" -> PYROCLAST_PALETTE.toColorList()
                "Trapper" -> TRAPPER_PALETTE.toColorList()
                "Hive" -> HIVE_PALETTE.toColorList()
                "Crashing Tide" -> CRASHING_TIDE_PALETTE.toColorList()
                "Deepwraith" -> DEEPWRAITH_PALETTE.toColorList()
                "Metal Mosaic" -> METAL_MOSAIC_PALETTE.toColorList()
                else -> emptyList()
            }
        }
    }

    fun getEnhancementTypeIcon(enhancementType: EnhancementType): Int {
        return when(enhancementType) {
            EnhancementType.AreaOfEffectEnhancement -> R.drawable.fh_hex_attack_color_icon
            EnhancementType.ElementEnhancementType -> R.drawable.circle
            EnhancementType.NegativeEnhancementType -> R.drawable.diamondnew
            EnhancementType.NeutralEnhancementType -> R.drawable.squarenew
            EnhancementType.PositiveEnhancementType -> R.drawable.plusdiamondnew
        }
    }

    fun getEnhancementIcon(name: String): Int {
        return when(name) {
            "Move +1" -> R.drawable.fh_move_bw_icon
            "Attack +1" -> R.drawable.fh_attack_bw_icon
            "Range +1" -> R.drawable.fh_range_bw_icon
            "Target +1" -> R.drawable.fh_target_bw_icon
            "Shield +1" -> R.drawable.fh_shield_bw_icon
            "Retaliate +1" -> R.drawable.fh_retaliate_bw_icon
            "Pierce +1" -> R.drawable.fh_pierce_color_icon
            "Heal +1" -> R.drawable.fh_heal_bw_icon
            "Push +1" -> R.drawable.fh_push_color_icon
            "Pull +1" -> R.drawable.fh_pull_color_icon
            "Teleport +1" -> R.drawable.fh_teleport_bw_icon
            "Summon HP +1" -> R.drawable.fh_heal_bw_icon
            "Summon Move +1" -> R.drawable.fh_move_bw_icon
            "Summon Attack +1" -> R.drawable.fh_attack_bw_icon
            "Summon Range +1" -> R.drawable.fh_range_bw_icon
            "Regenerate" -> R.drawable.fh_regenerate_color_icon
            "Ward" -> R.drawable.fh_ward_color_icon
            "Strengthen" -> R.drawable.fh_strengthen_color_icon
            "Bless" -> R.drawable.fh_bless_color_icon
            "Wound" -> R.drawable.fh_wound_color_icon
            "Poison" -> R.drawable.fh_poison_color_icon
            "Immobilize" -> R.drawable.fh_immobilize_color_icon
            "Muddle" -> R.drawable.fh_muddle_color_icon
            "Curse" -> R.drawable.fh_curse_color_icon
            "Element" -> R.drawable.fh_wild_color_icon
            "Wild Element" -> R.drawable.fh_wild_color_icon
            "Jump" -> R.drawable.fh_jump_bw_icon
            "Area-of-Effect Hex" -> R.drawable.fh_hex_attack_color_icon
            else -> R.drawable.baseline_star_border_24
        }
    }

    fun isEnhancementIconColored(name: String): Pair<Int, Boolean> {
        return when(name) {
            "Move +1" -> Pair(R.drawable.fh_move_bw_icon, false)
            "Attack +1" -> Pair(R.drawable.fh_attack_bw_icon, false)
            "Range +1" -> Pair(R.drawable.fh_range_bw_icon, false)
            "Target +1" -> Pair(R.drawable.fh_target_bw_icon, false)
            "Shield +1" -> Pair(R.drawable.fh_shield_bw_icon, false)
            "Retaliate +1" -> Pair(R.drawable.fh_retaliate_bw_icon, false)
            "Pierce +1" -> Pair(R.drawable.fh_pierce_color_icon, true)
            "Heal +1" -> Pair(R.drawable.fh_heal_bw_icon, false)
            "Push +1" -> Pair(R.drawable.fh_push_color_icon, true)
            "Pull +1" -> Pair(R.drawable.fh_pull_color_icon, true)
            "Teleport +1" -> Pair(R.drawable.fh_teleport_bw_icon, false)
            "Summon HP +1" -> Pair(R.drawable.fh_heal_bw_icon,false)
            "Summon Move +1" -> Pair(R.drawable.fh_move_bw_icon,false)
            "Summon Attack +1" -> Pair(R.drawable.fh_attack_bw_icon, false)
            "Summon Range +1" -> Pair(R.drawable.fh_range_bw_icon,false)
            "Regenerate" -> Pair(R.drawable.fh_regenerate_color_icon,true)
            "Ward" -> Pair(R.drawable.fh_ward_color_icon,true)
            "Strengthen" -> Pair(R.drawable.fh_strengthen_color_icon,true)
            "Bless" -> Pair(R.drawable.fh_bless_color_icon, true)
            "Wound" -> Pair(R.drawable.fh_wound_color_icon, true)
            "Poison" -> Pair(R.drawable.fh_poison_color_icon, true)
            "Immobilize" -> Pair(R.drawable.fh_immobilize_color_icon, true)
            "Muddle" -> Pair(R.drawable.fh_muddle_color_icon, true)
            "Curse" -> Pair(R.drawable.fh_curse_color_icon, true)
            "Element" -> Pair(R.drawable.fh_wild_color_icon, true)
            "Wild Element" -> Pair(R.drawable.fh_wild_color_icon, true)
            "Jump" -> Pair(R.drawable.fh_jump_bw_icon, false)
            "Area-of-Effect Hex" -> Pair(R.drawable.fh_hex_attack_color_icon,true)
            else -> Pair(R.drawable.baseline_star_border_24, false)
        }
    }


}