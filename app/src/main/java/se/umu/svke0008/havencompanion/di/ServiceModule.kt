package se.umu.svke0008.havencompanion.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import se.umu.svke0008.havencompanion.domain.service.EnhancementService
import se.umu.svke0008.havencompanion.domain.service.EnhancementServiceImpl
import se.umu.svke0008.havencompanion.domain.service.ScenarioService
import se.umu.svke0008.havencompanion.domain.service.ScenarioServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideScenarioService(): ScenarioService {
        return ScenarioServiceImpl()
    }


    @Provides
    @Singleton
    fun provideEnhancementService(): EnhancementService {
        return EnhancementServiceImpl()
    }

}