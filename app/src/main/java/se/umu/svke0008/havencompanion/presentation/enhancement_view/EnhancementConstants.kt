package se.umu.svke0008.havencompanion.presentation.enhancement_view

object EnhancementConstants {

    const val ENHANCEMENT_RULE_1 = "If the ability targets multiple figures or tiles, double the cost. " +
            "This applies to abilities that summon or affect multiple allies or tokens to abilities " +
            "that can target multiple figures or tiles. This does not apply to target, area-of-effect hex, " +
            "or element enhancements."

    const val ENHANCEMENT_RULE_2 = "If the action has a lost icon, but no persistent icon, halve the cost."
    const val ENHANCEMENT_RULE_3 = "If the ability provides a persistent bonus, whether or not the action has a lost icon, " +
            "triple the cost. This does not apply to summon stat enhancements."

    const val ENHANCEMENT_RULE_4 = "For each level of the ability card above level 1, add 25 gold to the cost."
    const val ENHANCEMENT_RULE_5 = "For each enhancement already on the action, add 75 gold to the cost."

    const val ENHANCER_LEVEL_1 = " - "
    const val ENHANCER_LEVEL_2 = "Reduce all enhancement costs by 10 gold."
    const val ENHANCER_LEVEL_3 = "Reduce all enhancement costs by 10 gold and level penalties by 10 gold per level."
    const val ENHANCER_LEVEL_4 = "Reduce all enhancement costs by 10 gold, level penalties by 10 gold per level, and repeat penalties by 25 gold per enhancement."

    const val AREA_OF_EFFECT_TOOLTIP = "200 Gold divided by the number of existing hexes (rounded up)"

}