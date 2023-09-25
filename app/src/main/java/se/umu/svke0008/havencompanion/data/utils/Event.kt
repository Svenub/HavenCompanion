package se.umu.svke0008.havencompanion.data.utils

data class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun avoidConsumeEventIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            content
        }
    }

    fun consumeEventIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T? = content

    /**
     * Marks the event as handled.
     */
    fun markAsHandled() {
        hasBeenHandled = true
    }
}
