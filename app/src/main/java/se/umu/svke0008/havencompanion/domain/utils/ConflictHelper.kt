package se.umu.svke0008.havencompanion.domain.utils

import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter

interface ConflictHelper {


    fun checkMonsterConflict(gameCharacters: List<GameCharacter.Monster>): List<GameCharacter.Monster>

    fun checkFirstInitiativeConflict(gameCharacters: List<GameCharacter.Hero>): List<GameCharacter.Hero>

    fun checkHeroConflict(gameCharacters: List<GameCharacter.Hero>): List<GameCharacter.Hero>

    fun checkLongRestConflict(gameCharacters: List<GameCharacter.Hero>): List<GameCharacter.Hero>




}