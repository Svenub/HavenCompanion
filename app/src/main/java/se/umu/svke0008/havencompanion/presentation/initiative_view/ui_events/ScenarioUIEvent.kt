package se.umu.svke0008.havencompanion.presentation.initiative_view.ui_events

import se.umu.svke0008.havencompanion.domain.utils.Utils.getFormattedDuration
import java.time.LocalTime

sealed class ScenarioUIEvent(open val message: String) {

    object Initial : ScenarioUIEvent(INITIAL_MESSAGE)
    object Canceled : ScenarioUIEvent(CANCELED_MESSAGE)
    data class Done(val round: Int, val startTime: LocalTime, val endTime: LocalTime) :
        ScenarioUIEvent(DONE_MESSAGE + " Round: $round. Duration: ${getFormattedDuration(startTime, endTime)}")

    companion object {
        const val INITIAL_MESSAGE = ""
        const val CANCELED_MESSAGE = "Initiative canceled."
        const val DONE_MESSAGE = "Initiative set."
    }

}


