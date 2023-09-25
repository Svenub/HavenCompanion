package se.umu.svke0008.havencompanion.domain.repository

import kotlinx.coroutines.flow.Flow
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult
import se.umu.svke0008.havencompanion.data.local.entities.enhancement_entity.EnhancementEntity

interface EnhancementRepository {

    fun getAllEnchantments(): Flow<DatabaseResult<List<EnhancementEntity>>>

    fun getAllNeutralEnchantments(): Flow<DatabaseResult<List<EnhancementEntity>>>

    fun getAllNegativeEnchantments(): Flow<DatabaseResult<List<EnhancementEntity>>>

    fun getAllPositiveEnchantments(): Flow<DatabaseResult<List<EnhancementEntity>>>

    fun getEnchantmentByPrice(price: Int): Flow<DatabaseResult<EnhancementEntity>>

    fun getAffordableEnchantments(price: Int): Flow<DatabaseResult<List<EnhancementEntity>>>
}
