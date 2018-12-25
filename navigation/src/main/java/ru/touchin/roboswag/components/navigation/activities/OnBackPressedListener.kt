package ru.touchin.roboswag.components.navigation.activities

/**
 * Interface to be implemented for someone who want to intercept device back button pressing event.
 */
interface OnBackPressedListener {

    /**
     * Calls when user presses device back button.
     *
     * @return True if it is processed by this object.
     */
    fun onBackPressed(): Boolean

}
