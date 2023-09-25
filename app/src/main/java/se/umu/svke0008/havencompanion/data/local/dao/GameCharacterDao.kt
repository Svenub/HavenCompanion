package se.umu.svke0008.havencompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity

@Dao
interface GameCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<GameCharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createNewCharacter(gameCharacter: GameCharacterEntity): Long

    @Delete
    suspend fun deleteCharacter(gameCharacter: GameCharacterEntity)

    @Query("SELECT * FROM game_characters")
    fun getAllGameCharacters(): Flow<List<GameCharacterEntity>>

    @Query("SELECT * FROM game_characters WHERE characterType='Hero'")
    fun getAllHeroes(): Flow<List<GameCharacterEntity>>

    @Query("SELECT * FROM game_characters WHERE characterType='Monster'")
    fun getAllMonsters():  Flow<List<GameCharacterEntity>>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateCharacter(gameCharacter: GameCharacterEntity)

}