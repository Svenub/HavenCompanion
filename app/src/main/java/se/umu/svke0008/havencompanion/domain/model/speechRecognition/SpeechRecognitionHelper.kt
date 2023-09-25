package se.umu.svke0008.havencompanion.domain.model.speechRecognition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * A helper class that provides speech recognition functionalities using Android's built-in SpeechRecognizer.
 *
 * This class wraps around the Android's SpeechRecognizer and provides a more streamlined interface for
 * recognizing speech, handling errors, and managing the recognition lifecycle.
 *
 * @property context The application context used for creating the SpeechRecognizer and accessing resources.
 * @constructor Creates a new instance of the SpeechRecognitionHelper and initializes the speech recognizer.
 */
class SpeechRecognitionHelper @Inject constructor(private val context: Context) :
    SpeechRecognition {

    private var speechRecognizer: SpeechRecognizer? = null
    private var speechRecognizerIntent: Intent? = null

    private val _speechResultFlow = MutableStateFlow<List<String>>(emptyList())
    override val speechResultFlow: Flow<List<String>> = _speechResultFlow.map { it }.distinctUntilChanged()

    private val _partialSpeechResultFlow = MutableStateFlow("")
    override val partialSpeechResultFlow: Flow<String> = _partialSpeechResultFlow.map { it }

    private val _errorFlow = MutableStateFlow<Int?>(null)
    override val errorFlow: StateFlow<Int?> = _errorFlow

    private val _isListening = MutableStateFlow(false)
    override val isListening: Flow<Boolean> = _isListening.map { it }

    private var startListeningTime = 0L

    init {
        initializeSpeechRecognizer()
    }


    /**
     * Initializes the speech recognizer and its intent.
     *
     * This method checks if speech recognition is available on the device and sets up the necessary intent
     * with the required parameters for speech recognition. It also sets up a recognition listener to handle
     * various recognition events.
     */
    private fun initializeSpeechRecognizer() {
        if (isSpeechRecognitionAvailable()) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {

               // putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3000L)
                putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 6000L)

                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                // putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "sv-SE")

                putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)


            }



        } else {
            Log.e(
                "initializeSpeechRecognizer",
                "Speech recognition is not supported on this device."
            )
        }


        val listener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
                // Ready for speech
                Log.e("onResults", "onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {
                Log.e("onResults", "onBeginningOfSpeech")
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Input level changed
            }

            override fun onBufferReceived(buffer: ByteArray) {
                // More sound has been received
            }

            override fun onEndOfSpeech() {
            }

            /**
             * Handles errors that occur during speech recognition.
             *
             * This method is triggered when an error occurs during the speech recognition process.
             * If the error is due to no match and the listening duration is less than 3.5 seconds,
             * it will restart the listening process. Otherwise, it logs the error and updates the
             * respective flows to reflect the error state.
             *
             * @param error An integer representing the error code. Common error codes can be found in
             *              the `SpeechRecognizer` class, e.g., `SpeechRecognizer.ERROR_NO_MATCH`.
             */
            override fun onError(error: Int) {
                val duration = System.currentTimeMillis() - startListeningTime
                Log.e("onResults", "duration: $duration")


                if (duration < 3500L && error == SpeechRecognizer.ERROR_NO_MATCH && _isListening.value) {
                  startListening()
                    return
                }

                Log.e("onResults", "error: $error")
                _isListening.update { false }
                _speechResultFlow.update { emptyList() }
                _partialSpeechResultFlow.update { "" }
                _errorFlow.update { error }
            }

            /**
             * Handles the final set of recognition results from the speech recognizer.
             *
             * This method is triggered when the speech recognizer has a final set of recognition results.
             * It restarts the listening process and updates the respective flows with the recognized speech results.
             *
             * Note: Due to the limitations of the `SpeechRecognizer`, the listening process is restarted to keep the recognizer active.
             * The `SpeechRecognizer` tends to terminate too quickly when it finishes with a sentence but needs a quick pause to talk again.
             * This might be a hazardous implementation and should be used with caution.
             *
             * @param results A bundle containing the recognized speech results.
             */
            override fun onResults(results: Bundle) {
                // A final set of recognition results is ready

                startListening()
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                if (matches != null) {
                    _speechResultFlow.update { matches }
                    val joinedMatches = matches.joinToString(" ")
                    _partialSpeechResultFlow.update { joinedMatches }
                    Log.e("onResults", matches.toString())

                } else
                    _speechResultFlow.update { emptyList() }


            }

            override fun onPartialResults(partialResults: Bundle) {
                // Intermediate recognition results are ready
                val matches =
                    partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                if (matches != null) {

                    // Display or use the partial results
                    val joinedMatches = matches.joinToString(" ")
                    _partialSpeechResultFlow.update { joinedMatches }


                } else
                    _partialSpeechResultFlow.update { "" }
            }

            override fun onEvent(eventType: Int, params: Bundle) {}

        }


        speechRecognizer?.setRecognitionListener(listener)
    }

    /**
     * Starts the speech recognition process.
     *
     * This method initiates the listening process, allowing the system to capture and recognize speech input.
     */
    override fun startListening() {
        _isListening.update { true }
        startListeningTime = System.currentTimeMillis()
        speechRecognizer?.startListening(speechRecognizerIntent)
    }

    /**
     * Stops the speech recognition process.
     *
     * This method terminates the listening process, stopping the system from capturing any further speech input.
     */
    override fun stopListening() {
        _isListening.update { false }
        speechRecognizer?.cancel()
        speechRecognizer?.stopListening()
    }

    /**
     * Checks if speech recognition is available on the device.
     *
     * @return Returns `true` if speech recognition is available, `false` otherwise.
     */
    private fun isSpeechRecognitionAvailable(): Boolean {
        return SpeechRecognizer.isRecognitionAvailable(context)
    }
}
