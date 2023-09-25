package se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state

enum class InitiativeUiState {
    In_play,
    Exhausted,
    Order;

    fun getName(): String {
        return name.replace("_", " ")
    }
}