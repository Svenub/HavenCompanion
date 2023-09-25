package se.umu.svke0008.havencompanion.domain.service

import se.umu.svke0008.havencompanion.data.local.entities.enhancement_entity.EnhancementEntity
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementRuleState
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementType
import kotlin.math.ceil

class EnhancementServiceImpl : EnhancementService {

    override fun calculateEnhancementCost(
        enhancement: EnhancementEntity,
        enhancementRuleState: EnhancementRuleState,
    ): Int {


        var cost = when {
            enhancementRuleState.enhancerLevel >= 2 -> enhancement.baseCost - ENHANCER_LEVEL_2_COST_REDUCTION
            else -> enhancement.baseCost
        }

        if(enhancementRuleState.enhancementType is EnhancementType.AreaOfEffectEnhancement) {
            cost = calcAreaOfEffectCost(cost, enhancementRuleState.existingHexes)
        }

        if (enhancementRuleState.multipleTargets && !isRuleOneException(enhancement)) {
            cost = calcRuleOne(cost)
        }

        if (enhancementRuleState.hasLostIcon && !enhancementRuleState.hasPersistentBonus) {
            cost = calcRuleTwo(cost)
        }

        if (enhancementRuleState.hasPersistentBonus && !isRuleThreeException(enhancement)) {
            cost = calcRuleThree(cost)
        }

        cost = calcRuleFour(cost, enhancementRuleState.abilityCardLevel, enhancementRuleState.enhancerLevel)
        cost = calcRuleFive(cost, enhancementRuleState.existingEnhancements, enhancementRuleState.enhancerLevel)

        return cost
    }


    private fun isRuleOneException(enhancement: EnhancementEntity):Boolean {
        return when {
            enhancement.enhancementType is EnhancementType.AreaOfEffectEnhancement -> true
            enhancement.enhancementType is EnhancementType.ElementEnhancementType -> true
            enhancement.name.contains("Summon", true) -> true
            enhancement.name.contains("Target", true) -> true
            else -> false
        }
    }
    private fun isRuleThreeException(enhancement: EnhancementEntity): Boolean {
        return when {
            enhancement.name.contains("Summon", true) -> true
            else -> false
        }
    }

    private fun calcAreaOfEffectCost(baseCost: Int, existingHexes: Int): Int {
        return ceil(baseCost.toFloat() / if (existingHexes == 0) 1 else existingHexes).toInt()
    }

    private fun calcRuleOne(cost: Int): Int = 2 * cost
    private fun calcRuleTwo(cost: Int): Int = cost / 2
    private fun calcRuleThree(cost: Int): Int = cost * 3


    private fun calcRuleFour(cost: Int, cardLevel: Int, enhancerLevel: Int): Int {
        return when {
            enhancerLevel >= 3 -> cost + (cardLevel - 1) * ENHANCER_LEVEL_3_4_LEVEL_PENALTY
            else -> cost + (cardLevel - 1) * DEFAULT_LEVEL_PENALTY
        }
    }

    private fun calcRuleFive(cost: Int, existingEnchantments: Int, enhancerLevel: Int): Int {
        return when {
            enhancerLevel >= 4 -> cost + (existingEnchantments * ENHANCER_LEVEL_4_REPEAT_PENALTY)
            else -> cost + (existingEnchantments * DEFAULT_REPEAT_PENALTY)
        }
    }

    companion object {
        const val ENHANCER_LEVEL_2_COST_REDUCTION = 10
        const val ENHANCER_LEVEL_3_4_LEVEL_PENALTY = 15
        const val DEFAULT_LEVEL_PENALTY = 25
        const val ENHANCER_LEVEL_4_REPEAT_PENALTY = 50
        const val DEFAULT_REPEAT_PENALTY = 75
    }

}