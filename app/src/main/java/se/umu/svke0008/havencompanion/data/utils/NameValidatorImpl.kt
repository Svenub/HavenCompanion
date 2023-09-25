package se.umu.svke0008.havencompanion.data.utils

import se.umu.svke0008.havencompanion.domain.utils.NameValidator
import se.umu.svke0008.havencompanion.domain.utils.Utils
import se.umu.svke0008.havencompanion.domain.utils.ValidationResult
import java.util.Locale

class NameValidatorImpl : NameValidator {

    override fun isNameValid(
        checkName: String,
        nameList: List<String>,
        threshold: Double,
        checkingAlias: Boolean,
        filterEmptyStrings: Boolean
    ): ValidationResult {

        if (nameList.isEmpty()) {
            return ValidationResult.Valid
        }

        val checkNameLower = checkName.lowercase(Locale.ROOT)

        // Check if the checkName is exactly equal to any name in the list
        val filterEmptyString = if (filterEmptyStrings) nameList.filter { it != "" } else nameList
        val identicalNameExists =
            filterEmptyString.any { it.lowercase(Locale.ROOT) == checkNameLower }

        if (identicalNameExists) {
            return if (!checkingAlias) ValidationResult.Invalid.SimilarName(checkName)
            else
                ValidationResult.Invalid.AliasTooSimilar(checkName)
        }

        for (name in filterEmptyString) {
            if (Utils.levenshteinDistancePercentage(
                    name.lowercase(Locale.ROOT),
                    checkNameLower
                ) >= threshold
            ) {
                return if (!checkingAlias) ValidationResult.Invalid.SimilarName(name)
                else
                    ValidationResult.Invalid.AliasTooSimilar(name)
            }
        }

        return ValidationResult.Valid
    }
}


