package se.umu.svke0008.havencompanion.domain.utils

sealed class CharacterResult {
    object Initial : CharacterResult()
    object Success : CharacterResult()
    data class Error(val message: String) : CharacterResult()
}