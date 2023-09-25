package se.umu.svke0008.havencompanion.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import se.umu.svke0008.havencompanion.data.local.dao.GameCharacterDao
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterType
import se.umu.svke0008.havencompanion.data.local.source.GameCharacterSource
import se.umu.svke0008.havencompanion.data.mappers.GameCharacterMapperImpl.toDomain
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.repository.CharacterRepository
import se.umu.svke0008.havencompanion.presentation.initiative_view.Messages.FAILURE_ADDED_NEW_CHARACTER
import se.umu.svke0008.havencompanion.presentation.initiative_view.Messages.FAILURE_DELETED_CHARACTERS
import se.umu.svke0008.havencompanion.presentation.initiative_view.Messages.FAILURE_FETCHING_HEROES
import se.umu.svke0008.havencompanion.presentation.initiative_view.Messages.FAILURE_FETCHING_MONSTERS
import se.umu.svke0008.havencompanion.presentation.initiative_view.Messages.FAILURE_UPDATED_CHARACTER
import se.umu.svke0008.havencompanion.presentation.initiative_view.Messages.SUCCESS_ADDED_NEW_CHARACTER
import se.umu.svke0008.havencompanion.presentation.initiative_view.Messages.SUCCESS_DELETED_CHARACTER
import se.umu.svke0008.havencompanion.presentation.initiative_view.Messages.SUCCESS_UPDATED_CHARACTER

class CharacterRepositoryImpl(
    private val dao: GameCharacterDao,
    private val gameCharacterSource: GameCharacterSource
) : CharacterRepository {

    override fun getHeroEntities(): Flow<DatabaseResult<List<GameCharacterEntity>>> {
        return dao.getAllHeroes()
            .transform { heroes ->
                emit(DatabaseResult.Loading)
                emit(DatabaseResult.Success(heroes.filter { it.characterType == GameCharacterType.Hero }))
            }
            .catch { e -> emit(DatabaseResult.Failure(FAILURE_FETCHING_HEROES, e)) }
            .flowOn(Dispatchers.IO)
    }


    override fun getMonsterEntities(): Flow<DatabaseResult<List<GameCharacterEntity>>> =
        dao.getAllMonsters().transform { monsters ->
            emit(DatabaseResult.Loading)
            emit(DatabaseResult.Success(monsters.filter { it.characterType == GameCharacterType.Monster }))
        }.catch { e ->
            emit(DatabaseResult.Failure(FAILURE_FETCHING_MONSTERS, e))
        }.flowOn(Dispatchers.IO)

    override fun getCharacterEntities(): Flow<DatabaseResult<List<GameCharacterEntity>>> =
        dao.getAllGameCharacters().transform { characters ->
            emit(DatabaseResult.Loading)
            emit(DatabaseResult.Success(characters))
        }.catch { e ->
            emit(DatabaseResult.Failure(FAILURE_DELETED_CHARACTERS, e))
        }.flowOn(Dispatchers.IO)

    override fun getHeroes(): Flow<DatabaseResult<List<GameCharacter.Hero>>> =
        dao.getAllHeroes().transform { heroes ->
            emit(DatabaseResult.Loading)
            emit(DatabaseResult.Success(heroes.map { it.toDomain() }
                .filterIsInstance<GameCharacter.Hero>()))
        }.catch { e ->
            emit(DatabaseResult.Failure(FAILURE_FETCHING_HEROES, e))
        }.flowOn(Dispatchers.IO)

    override fun getMonsters(): Flow<DatabaseResult<List<GameCharacter.Monster>>> =
        dao.getAllMonsters().transform { monsters ->
            emit(DatabaseResult.Loading)
            emit(DatabaseResult.Success(monsters.map { it.toDomain() }
                .filterIsInstance<GameCharacter.Monster>()))
        }.catch { e ->
            emit(DatabaseResult.Failure(FAILURE_FETCHING_MONSTERS, e))
        }.flowOn(Dispatchers.IO)

    override fun getAllCharacters(): Flow<DatabaseResult<List<GameCharacter>>> =
        dao.getAllGameCharacters().transform { characters ->
            emit(DatabaseResult.Loading)
            emit(DatabaseResult.Success(characters.map { it.toDomain() }))
        }.catch { e ->
            emit(DatabaseResult.Failure(FAILURE_DELETED_CHARACTERS, e))
        }

    override suspend fun createNewCharacter(gameCharacter: GameCharacterEntity): Flow<DatabaseResult<String>> =
        flow {
            emit(DatabaseResult.Loading)
            try {
                dao.createNewCharacter(gameCharacter)
                emit(DatabaseResult.Success(SUCCESS_ADDED_NEW_CHARACTER))
            } catch (e: Exception) {
                emit(DatabaseResult.Failure(FAILURE_ADDED_NEW_CHARACTER, e))
            }
        }


    override suspend fun deleteCharacter(gameCharacter: GameCharacterEntity): Flow<DatabaseResult<String>> =
        flow {
            emit(DatabaseResult.Loading)
            try {
                dao.deleteCharacter(gameCharacter)
                emit(DatabaseResult.Success(SUCCESS_DELETED_CHARACTER))
            } catch (e: Exception) {
                emit(DatabaseResult.Failure(FAILURE_DELETED_CHARACTERS, e))
            }
        }

    override suspend fun updateCharacter(gameCharacter: GameCharacterEntity): Flow<DatabaseResult<String>> =
        flow {
            emit(DatabaseResult.Loading)
            try {
                dao.updateCharacter(gameCharacter)
                emit(DatabaseResult.Success(SUCCESS_UPDATED_CHARACTER))
            } catch (e: Exception) {
                emit(DatabaseResult.Failure(FAILURE_UPDATED_CHARACTER, e))
            }
        }
}






