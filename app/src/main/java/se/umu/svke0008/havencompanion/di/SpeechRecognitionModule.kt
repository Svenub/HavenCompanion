package se.umu.svke0008.havencompanion.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import se.umu.svke0008.havencompanion.domain.model.speechRecognition.SpeechRecognition
import se.umu.svke0008.havencompanion.domain.model.speechRecognition.SpeechRecognitionHelper
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SpeechRecognitionModule {

    @Provides
    @Singleton
    fun provideSpeechRecognitionHelper(@ApplicationContext context: Context): SpeechRecognition {
        return SpeechRecognitionHelper(context)
    }

}