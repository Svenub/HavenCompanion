package se.umu.svke0008.havencompanion.domain.actions

import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity

sealed class CharacterAction {
    data class CreateCharacter(val character: GameCharacterEntity): CharacterAction()
    data class DeleteCharacter(val character: GameCharacterEntity): CharacterAction()
    data class UpdateCharacter(val character: GameCharacterEntity): CharacterAction()
    data class UnLockCharacter(val character: GameCharacterEntity) : CharacterAction()
    data class LockCharacter(val character: GameCharacterEntity): CharacterAction()
    object UndoDelete: CharacterAction()
    object UndoEdit: CharacterAction()

}
