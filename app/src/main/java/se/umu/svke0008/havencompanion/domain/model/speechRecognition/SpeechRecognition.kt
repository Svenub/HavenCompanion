package se.umu.svke0008.havencompanion.domain.model.speechRecognition

import kotlinx.coroutines.flow.Flow

/**
 * An interface that defines the contract for speech recognition functionalities.
 *
 * This interface provides a set of methods and properties that enable the implementation
 * of speech recognition capabilities. It provides real-time feedback on recognized speech,
 * partial results, listening status, and potential errors.
 */
interface SpeechRecognition {

    /**
     * A flow that emits a list of recognized speech results.
     *
     * This flow provides the final recognized results from the speech input.
     */
    val speechResultFlow: Flow<List<String>>

    /**
     * A flow that emits partial speech recognition results.
     *
     * This flow provides real-time feedback on the ongoing speech recognition process,
     * emitting partial results as the user speaks.
     */
    val partialSpeechResultFlow: Flow<String>

    /**
     * A flow that indicates the listening status of the speech recognition.
     *
     * This flow emits `true` when the speech recognition is actively listening and `false` otherwise.
     */
    val isListening: Flow<Boolean>

    /**
     * A flow that emits error codes related to the speech recognition process.
     *
     * This flow provides feedback on any errors that might occur during the speech recognition process.
     * The emitted integer values represent specific error codes.
     */
    val errorFlow: Flow<Int?>

    /**
     * Starts the speech recognition process.
     *
     * This method initiates the listening process, allowing the system to capture and recognize speech input.
     */
    fun startListening()

    /**
     * Stops the speech recognition process.
     *
     * This method terminates the listening process, stopping the system from capturing any further speech input.
     */
    fun stopListening()
}
