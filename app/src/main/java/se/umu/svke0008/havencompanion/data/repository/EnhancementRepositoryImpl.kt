package se.umu.svke0008.havencompanion.data.repository

import kotlinx.coroutines.flow.Flow
import se.umu.svke0008.havencompanion.data.local.dao.EnhancementDao
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult
import se.umu.svke0008.havencompanion.data.local.db.toDatabaseResult
import se.umu.svke0008.havencompanion.data.local.entities.enhancement_entity.EnhancementEntity
import se.umu.svke0008.havencompanion.domain.repository.EnhancementRepository


class EnhancementRepositoryImpl (private val dao: EnhancementDao) : EnhancementRepository {

    override fun getAllEnchantments(): Flow<DatabaseResult<List<EnhancementEntity>>> {
        return dao.getAllEnchantments().toDatabaseResult()
    }

    override fun getAllNeutralEnchantments(): Flow<DatabaseResult<List<EnhancementEntity>>> {
        return dao.getAllNeutralEnchantments().toDatabaseResult()
    }

    override fun getAllNegativeEnchantments(): Flow<DatabaseResult<List<EnhancementEntity>>> {
        return dao.getAllNegativeEnchantments().toDatabaseResult()
    }

    override fun getAllPositiveEnchantments(): Flow<DatabaseResult<List<EnhancementEntity>>> {
        return dao.getAllPositiveEnchantments().toDatabaseResult()
    }

    override fun getEnchantmentByPrice(price: Int): Flow<DatabaseResult<EnhancementEntity>> {
        return dao.getEnchantmentByPrice(price).toDatabaseResult()
    }

    override fun getAffordableEnchantments(price: Int): Flow<DatabaseResult<List<EnhancementEntity>>> {
        return dao.getAffordableEnchantments(price).toDatabaseResult()
    }
}

