package se.umu.svke0008.havencompanion.presentation.initiative_view.ui_events

sealed class CharacterOperationUIEvent(open val message: String) {


    val timeStamp: Long = System.currentTimeMillis()

    object NoEvent: CharacterOperationUIEvent(NO_NEW_EVENT)

    object Loading: CharacterOperationUIEvent("Loading")

    sealed class CreateCharacterUIEvent(message: String) : CharacterOperationUIEvent(message) {
        data class Success(override val message: String = SUCCESS_CREATE_NEW_CHARACTER) :
            CharacterOperationUIEvent(message)

        data class Error(override val message: String = ERROR_CREATE_NEW_CHARACTER) :
            CharacterOperationUIEvent(message)
    }

    sealed class DeleteCharacterUIEvent(message: String) : CharacterOperationUIEvent(message) {
        data class Success(override val message: String = SUCCESS_DELETE_CHARACTER) :
            CharacterOperationUIEvent(message)

        data class Error(override val message: String = ERROR_DELETE_CHARACTER) :
            CharacterOperationUIEvent(message)
    }

    sealed class UpdateCharacterUIEvent(message: String) : CharacterOperationUIEvent(message) {
        data class Success(override val message: String = SUCCESS_UPDATE_CHARACTER) :
            CharacterOperationUIEvent(message)

        data class Error(override val message: String = ERROR_UPDATE_CHARACTER) :
            CharacterOperationUIEvent(message)
    }

    companion object {
        const val NO_NEW_EVENT = "No new event"

        const val SUCCESS_CREATE_NEW_CHARACTER = "Successfully created a new character!"
        const val SUCCESS_UPDATE_CHARACTER = "Character updated!"
        const val SUCCESS_DELETE_CHARACTER = "Character deleted!"

        const val ERROR_CREATE_NEW_CHARACTER = "Failed to create a new character"
        const val ERROR_UPDATE_CHARACTER = "Failed to update character"
        const val ERROR_DELETE_CHARACTER = "Failed to delete character"
    }

    override fun toString(): String {
        return "CharacterOperationUIEvent(message='$message')"
    }


}

