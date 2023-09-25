package se.umu.svke0008.havencompanion.domain.repository

import kotlinx.coroutines.flow.Flow
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter

/**
 * An interface that defines the contract for a character repository.
 *
 * The `CharacterRepository` serves as a data access layer for retrieving character-related information,
 * such as heroes, monsters, and all characters. Implementations of this interface can source data from
 * various backends like databases, network, or in-memory caches.
 */
interface CharacterRepository {

    fun getHeroEntities(): Flow<DatabaseResult<List<GameCharacterEntity>>>

    fun getMonsterEntities(): Flow<DatabaseResult<List<GameCharacterEntity>>>

    fun getCharacterEntities(): Flow<DatabaseResult<List<GameCharacterEntity>>>

    fun getHeroes(): Flow<DatabaseResult<List<GameCharacter.Hero>>>

    fun getMonsters(): Flow<DatabaseResult<List<GameCharacter.Monster>>>

    fun getAllCharacters(): Flow<DatabaseResult<List<GameCharacter>>>

    suspend fun createNewCharacter(gameCharacter: GameCharacterEntity): Flow<DatabaseResult<String>>

    suspend fun deleteCharacter(gameCharacter: GameCharacterEntity): Flow<DatabaseResult<String>>

    suspend fun updateCharacter(gameCharacter: GameCharacterEntity) : Flow<DatabaseResult<String>>

    companion object {
        const val RESTORE_CHARACTER = "RESTORE_CHARACTER"
    }

}




