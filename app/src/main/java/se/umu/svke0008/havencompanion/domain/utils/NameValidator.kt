package se.umu.svke0008.havencompanion.domain.utils

/**
 * Interface responsible for validating names against a collection of existing names.
 */
interface NameValidator {

    /**
     * Validates the provided name against a list of existing names to ensure uniqueness and prevent overly similar names.
     *
     * @param checkName The name that needs to be validated.
     * @param nameList A collection of existing names against which the [checkName] will be checked.
     * @param threshold A value between 0 and 1 that determines the similarity threshold for validation. A higher value is more lenient. Defaults to [DEFAULT_CHECK_NAME_THRESHOLD].
     * @param checkingAlias A flag indicating whether the name being checked is an alias.
     * @return [ValidationResult] which indicates the outcome of the validation process.
     */
    fun isNameValid(
        checkName: String,
        nameList: List<String>,
        threshold: Double = DEFAULT_CHECK_NAME_THRESHOLD,
        checkingAlias: Boolean,
        filterEmptyStrings: Boolean = true
    ): ValidationResult

    companion object {
        /** The default similarity threshold used during name validation. */
        const val DEFAULT_CHECK_NAME_THRESHOLD = 0.6
        /** A more lenient similarity threshold for name validation. */
        const val LENIENT_THRESHOLD = 0.8
    }


}

/**
 * Represents the result of a name validation process.
 */
sealed class ValidationResult {

    object Initial : ValidationResult()

    object Valid : ValidationResult()

    sealed class Invalid : ValidationResult() {
        data class Other(val message: String): Invalid()
        data class SimilarName(val similarName: String) : Invalid()
        data class AliasTooSimilar(val similarAlias: String) : Invalid()

    }
    
    companion object {
        const val SIMILAR_CHARACTER_NAME = "Name is similar another character's name"
        const val SIMILAR_ALIAS_NAME = "Alias is similar another character's alias name"
    }


}
