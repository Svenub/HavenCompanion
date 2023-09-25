package se.umu.svke0008.havencompanion.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import se.umu.svke0008.havencompanion.data.utils.NameValidatorImpl
import se.umu.svke0008.havencompanion.domain.utils.NameValidator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {

    @Singleton
    @Provides
    fun provideNameValidator(): NameValidator = NameValidatorImpl()
}
