package se.umu.svke0008.havencompanion.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import se.umu.svke0008.havencompanion.data.local.dao.EnhancementDao
import se.umu.svke0008.havencompanion.data.local.dao.GameCharacterDao
import se.umu.svke0008.havencompanion.data.local.db.CompanionDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCompanionDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CompanionDatabase::class.java,
        CompanionDatabase.DATABASE_NAME
    ).createFromAsset("database/haven_companion.db").build()



    @Provides
    fun provideGameCharacterDao(database: CompanionDatabase): GameCharacterDao {
        return database.gameCharacterDao
    }

    @Provides
    fun provideEnhancementDao(database: CompanionDatabase): EnhancementDao {
        return database.enhancementDao
    }

}