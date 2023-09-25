package se.umu.svke0008.havencompanion.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import se.umu.svke0008.havencompanion.data.local.dao.EnhancementDao
import se.umu.svke0008.havencompanion.data.local.dao.GameCharacterDao
import se.umu.svke0008.havencompanion.data.local.source.GameCharacterSource
import se.umu.svke0008.havencompanion.data.repository.CharacterRepositoryImpl
import se.umu.svke0008.havencompanion.data.repository.EnhancementRepositoryImpl
import se.umu.svke0008.havencompanion.domain.repository.CharacterRepository
import se.umu.svke0008.havencompanion.domain.repository.EnhancementRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(gameCharacterDao: GameCharacterDao): CharacterRepository {
        return CharacterRepositoryImpl(gameCharacterDao, GameCharacterSource)
    }

    @Provides
    @Singleton
    fun provideEnhancementRepository(enhancementDao: EnhancementDao): EnhancementRepository {
        return EnhancementRepositoryImpl(enhancementDao)
    }

}