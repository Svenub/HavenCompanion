package se.umu.svke0008.havencompanion.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import se.umu.svke0008.havencompanion.data.local.converters.Converters
import se.umu.svke0008.havencompanion.data.local.dao.EnhancementDao
import se.umu.svke0008.havencompanion.data.local.dao.GameCharacterDao
import se.umu.svke0008.havencompanion.data.local.dao.ScenarioDao
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.local.entities.enhancement_entity.EnhancementEntity


@Database(entities = [GameCharacterEntity::class, EnhancementEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class CompanionDatabase: RoomDatabase() {

    abstract val scenarioDao: ScenarioDao
    abstract val gameCharacterDao: GameCharacterDao
    abstract val enhancementDao: EnhancementDao

    companion object {
        const val DATABASE_NAME = "haven_companion"
    }
}

