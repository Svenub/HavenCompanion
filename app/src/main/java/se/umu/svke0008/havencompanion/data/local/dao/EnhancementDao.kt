package se.umu.svke0008.havencompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import se.umu.svke0008.havencompanion.data.local.entities.enhancement_entity.EnhancementEntity

@Dao
interface EnhancementDao {

    @Query("SELECT * FROM enhancement_table")
    fun getAllEnchantments(): Flow<List<EnhancementEntity>>

    @Query("SELECT * FROM enhancement_table WHERE enhancementType ='Neutral'")
    fun getAllNeutralEnchantments(): Flow<List<EnhancementEntity>>

    @Query("SELECT * FROM enhancement_table WHERE enhancementType ='Negative'")
    fun getAllNegativeEnchantments(): Flow<List<EnhancementEntity>>

    @Query("SELECT * FROM enhancement_table WHERE enhancementType ='Positive'")
    fun getAllPositiveEnchantments(): Flow<List<EnhancementEntity>>

    @Query("SELECT * FROM enhancement_table WHERE baseCost = :price")
    fun getEnchantmentByPrice(price: Int): Flow<EnhancementEntity>

    @Query("SELECT * FROM enhancement_table WHERE baseCost <= :price")
    fun getAffordableEnchantments(price: Int): Flow<List<EnhancementEntity>>
}
